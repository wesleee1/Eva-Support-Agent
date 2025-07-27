package com.eva.EvaSupportAgent.agent.mapper;

import org.mapstruct.Mapper;

import com.eva.EvaSupportAgent.agent.model.Tool;
import com.eva.EvaSupportAgent.agent.vo.ToolVO;

@Mapper(componentModel = "spring")
public interface ToolMapper {
	Tool toEntity(ToolVO toolVO);
}
