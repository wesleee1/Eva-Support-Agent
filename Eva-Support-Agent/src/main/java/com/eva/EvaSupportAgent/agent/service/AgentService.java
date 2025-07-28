package com.eva.EvaSupportAgent.agent.service;

import java.util.List;

import com.eva.EvaSupportAgent.agent.model.Agent;
import com.eva.EvaSupportAgent.agent.vo.AgentVO;

public interface AgentService {
	public AgentVO createAgent(AgentVO agentVO);

	public AgentVO updateAgent(Long id, AgentVO agentVO);

	public AgentVO getAgent(Long id);

	public List<AgentVO> getAllAgents();

}
