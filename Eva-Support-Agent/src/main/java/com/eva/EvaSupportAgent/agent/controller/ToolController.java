package com.eva.EvaSupportAgent.agent.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eva.EvaSupportAgent.agent.service.ToolService;
import com.eva.EvaSupportAgent.agent.vo.ToolVO;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class ToolController {

	private final ToolService toolService;
	
	@PostMapping("/addTool/{agentId}")
	public ResponseEntity<?> addTool(@PathVariable Long agentId, @RequestBody ToolVO toolVO) {
		try {
			ToolVO savedTool = toolService.createTool(agentId, toolVO);
			return ResponseEntity.ok(savedTool);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding tool: " + e.getMessage());
		}
	}

	@GetMapping("/getAllTools/{agentId}")
	public ResponseEntity<?> getToolsByAgent(@PathVariable Long agentId) {
		try {
			List<ToolVO> tools = toolService.getToolsByAgent(agentId);
			return ResponseEntity.ok(tools);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error fetching tools: " + e.getMessage());
		}
	}

	@DeleteMapping("deleteTool/{toolId}")
	public ResponseEntity<?> deleteTool(@PathVariable Long toolId) {
		try {
			toolService.deleteTool(toolId);
			return ResponseEntity.ok("Tool deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting tool: " + e.getMessage());
		}
	}
}
