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

@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

	private final ToolRepository toolRepository;
	private final AgentRepository agentRepository;
	private final ToolMapper toolMapper;

	@Override
	public Tool addTool(Long agentId, ToolVO toolVO) {
		Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new RuntimeException("Agent not found"));

		Tool tool = toolMapper.toEntity(toolVO);
		tool.setAgent(agent);

		return toolRepository.save(tool);
	}

	@Override
	public List<Tool> getToolsByAgent(Long agentId) {
		return toolRepository.findByAgentId(agentId);
	}

	@Override
	public void deleteTool(Long toolId) {
		toolRepository.deleteById(toolId);
	}
}
