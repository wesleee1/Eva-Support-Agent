package com.eva.EvaSupportAgent.agent.mapper;

import com.eva.EvaSupportAgent.agent.model.Agent;
import com.eva.EvaSupportAgent.agent.vo.AgentVO;

public class AgentMapper {

	public static Agent toEntity(AgentVO vo) {

		Agent agent = new Agent();
		agent.setName(vo.getName());
		agent.setLanguage(vo.getLanguage());
		agent.setVoiceModel(vo.getVoiceModel());
		agent.setLlmParameters(vo.getLlmParameters());
		agent.setFirstMessage(vo.getFirstMessage());
		agent.setSystemPrompt(vo.getSystemPromt());
		return agent;
	}

	public static AgentVO toVO(Agent agent) {
		AgentVO vo = new AgentVO();
		vo.setName(agent.getName());
		vo.setLanguage(agent.getLanguage());
		vo.setVoiceModel(agent.getVoiceModel());
		vo.setLlmParameters(agent.getLlmParameters());
		vo.setFirstMessage(agent.getFirstMessage());
		vo.setSystemPromt(agent.getSystemPrompt());
		vo.setAgentId(agent.getId());
		return vo;
	}

}
