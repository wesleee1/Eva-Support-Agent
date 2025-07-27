package com.eva.EvaSupportAgent.agent.service;

import java.util.List;

import com.eva.EvaSupportAgent.agent.model.Agent;
import com.eva.EvaSupportAgent.agent.vo.AgentVO;

public interface AgentService {
	public Agent createAgent(AgentVO agentVO);

	public Agent updateAgent(Long id, AgentVO agentVO);

	public Agent getAgent(Long id);

	public List<Agent> getAllAgents();

}
