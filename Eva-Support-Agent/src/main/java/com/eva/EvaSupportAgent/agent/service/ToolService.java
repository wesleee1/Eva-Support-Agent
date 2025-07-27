package com.eva.EvaSupportAgent.agent.service;

import java.util.List;

import com.eva.EvaSupportAgent.agent.model.Tool;
import com.eva.EvaSupportAgent.agent.vo.ToolVO;

public interface ToolService {
	Tool addTool(Long agentId, ToolVO toolVO);

	List<Tool> getToolsByAgent(Long agentId);

	void deleteTool(Long toolId);
}
