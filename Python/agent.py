from dotenv import load_dotenv
import os
import httpx
from livekit import agents
from livekit.agents import AgentSession, Agent, RoomInputOptions
from livekit.plugins import (
    openai,
    cartesia,
    deepgram,
    noise_cancellation,
    silero,
)
from livekit.plugins.turn_detector.multilingual import MultilingualModel
import inspect
from datetime import datetime

# ===== PATCH: Make supports_language and unlikely_threshold async if they're not already ======
if not hasattr(MultilingualModel, "__supports_language_asyncified"):
    orig = MultilingualModel.supports_language
    if not inspect.iscoroutinefunction(orig):
        async def supports_language_async(self, lang): return orig(self, lang)
        MultilingualModel.supports_language = supports_language_async
        MultilingualModel.__supports_language_asyncified = True

if not hasattr(MultilingualModel, "__unlikely_threshold_asyncified"):
    orig_ut = MultilingualModel.unlikely_threshold
    if not inspect.iscoroutinefunction(orig_ut):
        async def unlikely_threshold_async(self, lang): return orig_ut(self, lang)
        MultilingualModel.unlikely_threshold = unlikely_threshold_async
        MultilingualModel.__unlikely_threshold_asyncified = True
# ====================================================================

# Load environment variables from .env
load_dotenv()
SPRING_BOOT_BASE = os.getenv("SPRING_BOOT_API", "http://localhost:8080/api")

async def fetch_all_orders():
    url = f"{SPRING_BOOT_BASE}/order-history"
    async with httpx.AsyncClient(timeout=10.0) as client:
        resp = await client.get(url)
        resp.raise_for_status()
        return resp.json()

def format_order(order: dict) -> str:
    if not order:
        return "I couldn't find any recent orders for you."
    items = ", ".join([item.get("productName", "item") for item in order.get("items", [])])
    status = order.get("status", "unknown")
    total = order.get("total", "unknown")
    order_id = order.get("id", "unknown")
    order_date = order.get("orderDate", "recently")
    return (
        f"Your last order (Order ID: {order_id}) placed on {order_date} "
        f"includes: {items}. Order status is {status}. Total amount is ${total}."
    )

class Assistant(Agent):
    def __init__(self, order_data=None):
        # Format the instructions with order details
        instructions = self._format_instructions(order_data)
        super().__init__(instructions=instructions)
    
    def _format_instructions(self, order_data) -> str:
        base_instructions = (
            "You are an EVA Customer Support Agent. Your role is to assist customers "
            "with their orders and provide helpful information. Be polite, professional "
            "and concise in your responses. Always verify order details before sharing "
            "specific information with customers. Ask customers name initially. If the customer name didn't match the below details tell them that the order details does not exist in our system kindly contact our support team."
        )
        
        if not order_data:
            return base_instructions
        
        try:
            # Handle case where order_data might be a single order
            if isinstance(order_data, dict):
                order_data = [order_data]
            
            if not isinstance(order_data, list):
                return base_instructions
            
            # Create both a detailed version for the agent and a summary for customers
            agent_details = []
            customer_summaries = []
            
            for order in order_data:
                # Parse and format the date
                ordered_date = datetime.strptime(
                    order['orderedOn'], 
                    '%Y-%m-%dT%H:%M:%S.%f%z'
                ).strftime('%B %d, %Y') 
                
                # Format for the agent's reference
                agent_details.append(
                    f"Order ID: {order.get('id', 'N/A')}\n"
                    f"Product: {order.get('productName', 'Unknown product')}\n"
                    f"Quantity: {order.get('quantity', 1)}\n"
                    f"Status: {order.get('status', 'unknown')}\n"
                    f"Price: ${order.get('price', 0):.2f}\n"
                    f"Ordered on: {ordered_date}\n"
                    f"Ordered by: john\n"
                )
                
                # Create natural language summary for customers
                status = order.get('status', '').lower()
                product = order.get('productName', 'your item')
                quantity = order.get('quantity', 1)
                price = order.get('price', 0)
                
                summary = (
                    f"On {ordered_date}, you ordered {quantity} {product} for ${price:.2f}. "
                    f"This order is currently {status}."
                )
                customer_summaries.append(summary)
            
            # Combine everything
            full_instructions = (
                f"{base_instructions}\n\n"
                "CUSTOMER ORDER DETAILS (for your reference):\n"
                f"{'='*50}\n"
                f"{'\n\n'.join(agent_details)}\n"
                f"{'='*50}\n\n"
                "READY-TO-USE CUSTOMER SUMMARY:\n"
                f"{'='*50}\n"
                f"{'\n\n'.join(customer_summaries)}\n"
                f"{'='*50}\n\n"
                "When discussing orders with customers:\n"
                "- Use the 'READY-TO-USE CUSTOMER SUMMARY' as a base for your responses\n"
                "- Verify details from the reference section before sharing specifics\n"
                "- Never share order IDs unless the customer provides verification\n"
                "- For shipped orders, offer to provide tracking information\n"
                "- For processing orders, provide estimated timelines\n"
            )
            
            return full_instructions
            
        except Exception as e:
            print(f"Error formatting order details: {e}")
            return base_instructions

async def entrypoint(ctx: agents.JobContext):
    # First call the Spring Boot endpoint
    springboot_url = os.getenv("SPRINGBOOT_API_URL", "http://localhost:8080/api/order-history")
    
    try:
        async with httpx.AsyncClient() as client:
            response = await client.get(springboot_url, timeout=60.0)
            response.raise_for_status()
            print(f"Response status: {response.status_code}")
            print(f"Response headers: {response.headers}")
            try:
                order_data = response.json()
                print("Spring Boot response:", order_data)
            except ValueError as e:
                print(f"Failed to parse JSON: {e}")
                print(f"Raw response: {response.text}")
    except Exception as e:
        print(f"Failed to call Spring Boot endpoint: {e}")

    # Then proceed with your existing session setup
    session = AgentSession(
        stt=deepgram.STT(
            model=os.getenv("DEEPGRAM_MODEL"),
            language=os.getenv("DEEPGRAM_LANGUAGE"),
            api_key=os.getenv("DEEPGRAM_API_KEY"),
        ),
        llm=openai.LLM(
            model=os.getenv("OPENAI_MODEL"),
            api_key=os.getenv("OPENAI_API_KEY"),
        ),
        tts=cartesia.TTS(
            model=os.getenv("CARTESIA_MODEL"),
            voice=os.getenv("CARTESIA_VOICE"),
            api_key=os.getenv("CARTESIA_API_KEY"),
        ),
        vad=silero.VAD.load(),
        turn_detection=MultilingualModel(),
    )
    
    await session.start(
        room=ctx.room,
        agent=Assistant(order_data=order_data),
        room_input_options=RoomInputOptions(
            noise_cancellation=noise_cancellation.BVC(),
        ),
    )
    await ctx.connect()

    await session.generate_reply(
        instructions="You are an EVA Customer Support Agent. Greet the user politely."
    )

if __name__ == "__main__":
    agents.cli.run_app(agents.WorkerOptions(entrypoint_fnc=entrypoint))
