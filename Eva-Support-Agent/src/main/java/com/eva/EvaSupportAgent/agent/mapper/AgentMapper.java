package com.eva.EvaSupportAgent.agent.mapper;

import org.mapstruct.Mapper;

import com.eva.EvaSupportAgent.agent.model.Agent;
import com.eva.EvaSupportAgent.agent.vo.AgentVO;

@Mapper(componentModel = "spring")
public interface AgentMapper {
	Agent toEntity(AgentVO agentVO);
}
