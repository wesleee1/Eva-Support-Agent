package com.eva.EvaSupportAgent.agent.service;

import java.util.List;

import com.eva.EvaSupportAgent.agent.model.Tool;
import com.eva.EvaSupportAgent.agent.vo.AgentVO;
import com.eva.EvaSupportAgent.agent.vo.ToolVO;

public interface ToolService {
		
	public ToolVO createTool(Long agentId, ToolVO toolVO);

	public List<ToolVO> getToolsByAgent(Long agentId);

	public void deleteTool(Long toolId);
}
