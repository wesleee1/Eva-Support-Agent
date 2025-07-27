package com.eva.EvaSupportAgent.agent.service;

import com.eva.EvaSupportAgent.agent.mapper.AgentMapper;
import com.eva.EvaSupportAgent.agent.model.Agent;
import com.eva.EvaSupportAgent.agent.repository.AgentRepository;
import com.eva.EvaSupportAgent.agent.vo.AgentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

	private final AgentRepository agentRepository;
	private final AgentMapper agentMapper;

	@Override
	public Agent createAgent(AgentVO agentVO) {
		Agent agent = agentMapper.toEntity(agentVO);
		agent.setCreatedAt(LocalDateTime.now());
		agent.setUpdatedAt(LocalDateTime.now());
		return agentRepository.save(agent);
	}

	@Override
	public Agent updateAgent(Long id, AgentVO agentVO) {
		Agent agent = agentRepository.findById(id).orElseThrow(() -> new RuntimeException("Agent not found"));

		// MapStruct mapping for updates (manual update to avoid replacing the entity)
		agent.setName(agentVO.getName());
		agent.setLanguage(agentVO.getLanguage());
		agent.setVoiceModel(agentVO.getVoiceModel());
		agent.setLlmParameters(agentVO.getLlmParameters());
		agent.setUpdatedAt(LocalDateTime.now());

		return agentRepository.save(agent);
	}

	@Override
	public Agent getAgent(Long id) {
		return agentRepository.findById(id).orElseThrow(() -> new RuntimeException("Agent not found"));
	}

	@Override
	public List<Agent> getAllAgents() {
		return agentRepository.findAll();
	}
}
