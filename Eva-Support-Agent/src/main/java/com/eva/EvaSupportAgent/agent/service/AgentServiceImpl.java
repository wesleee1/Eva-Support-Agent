package com.eva.EvaSupportAgent.agent.service;

import com.eva.EvaSupportAgent.agent.mapper.AgentMapper;
import com.eva.EvaSupportAgent.agent.model.Agent;
import com.eva.EvaSupportAgent.agent.repository.AgentRepository;
import com.eva.EvaSupportAgent.agent.vo.AgentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

	private final AgentRepository agentRepository;

	@Override
	public AgentVO createAgent(AgentVO agentVO) {
		Agent agent = AgentMapper.toEntity(agentVO);
		agent.setCreatedAt(LocalDateTime.now());
		agent.setUpdatedAt(LocalDateTime.now());
		return AgentMapper.toVO(agentRepository.save(agent));
	}

	@Override
	public AgentVO updateAgent(Long id, AgentVO agentVO) {
		Agent agent = agentRepository.findById(id).orElseThrow(() -> new RuntimeException("Agent not found"));

		agent.setName(agentVO.getName());
		agent.setLanguage(agentVO.getLanguage());
		agent.setVoiceModel(agentVO.getVoiceModel());
		agent.setLlmParameters(agentVO.getLlmParameters());
		agent.setFirstMessage(agentVO.getFirstMessage());
		agent.setSystemPrompt(agentVO.getSystemPromt());
		agent.setUpdatedAt(LocalDateTime.now());

		return AgentMapper.toVO(agentRepository.save(agent));
	}

	@Override
	public AgentVO getAgent(Long id) {
		Agent agent= agentRepository.findById(id).orElseThrow(() -> new RuntimeException("Agent not found"));
		return AgentMapper.toVO(agent);
	}

	@Override
	public List<AgentVO> getAllAgents() {
		List<Agent> agents =agentRepository.findAll();
		List<AgentVO> agentVOs = agents.stream()
                .map(AgentMapper::toVO)
                .collect(Collectors.toList());

		return agentVOs;
	}
}
