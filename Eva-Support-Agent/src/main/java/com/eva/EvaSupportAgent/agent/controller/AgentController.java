package com.eva.EvaSupportAgent.agent.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eva.EvaSupportAgent.agent.model.Agent;
import com.eva.EvaSupportAgent.agent.service.AgentService;
import com.eva.EvaSupportAgent.agent.vo.AgentVO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {
	private final AgentService agentService;

	@PostMapping("addAgent")
	public ResponseEntity<?> createAgent(@RequestBody AgentVO agentVO) {
		try {
			AgentVO savedAgent = agentService.createAgent(agentVO);
			return ResponseEntity.ok(savedAgent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error creating agent: " + e.getMessage());
		}
	}

	@PutMapping("updateAgent/{id}")
	public ResponseEntity<?> updateAgent(@PathVariable Long id, @RequestBody AgentVO agentVO) {
		try {
			AgentVO updatedAgent = agentService.updateAgent(id, agentVO);
			return ResponseEntity.ok(updatedAgent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error updating agent: " + e.getMessage());
		}
	}

	@GetMapping("getAgent/{id}")
	public ResponseEntity<?> getAgent(@PathVariable Long id) {
		try {
			AgentVO agent = agentService.getAgent(id);
			return ResponseEntity.ok(agent);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agent not found: " + e.getMessage());
		}
	}

	@GetMapping("getAllAgent")
	public ResponseEntity<?> getAllAgents() {
		try {
			List<AgentVO> agents = agentService.getAllAgents();
			return ResponseEntity.ok(agents);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error fetching agents: " + e.getMessage());
		}
	}
}
