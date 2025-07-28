package com.eva.EvaSupportAgent.agent.service;

import com.eva.EvaSupportAgent.agent.mapper.ToolMapper;
import com.eva.EvaSupportAgent.agent.model.Agent;
import com.eva.EvaSupportAgent.agent.model.Tool;
import com.eva.EvaSupportAgent.agent.repository.AgentRepository;
import com.eva.EvaSupportAgent.agent.repository.ToolRepository;
import com.eva.EvaSupportAgent.agent.vo.ToolVO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

	private final ToolRepository toolRepository;
	private final AgentRepository agentRepository;

	@Override
	public ToolVO createTool(Long agentId, ToolVO toolVO) {
		Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new RuntimeException("Agent not found"));

		Tool tool = ToolMapper.toEntity(toolVO);
		tool.setAgent(agent);

		return ToolMapper.toVO(toolRepository.save(tool));
	}

	@Override
	public List<ToolVO> getToolsByAgent(Long agentId) {
		
		return toolRepository.findByAgentId(agentId).stream()
        .map(ToolMapper::toVO)
        .collect(Collectors.toList());
	}

	@Override
	public void deleteTool(Long toolId) {
		toolRepository.deleteById(toolId);
	}
}
