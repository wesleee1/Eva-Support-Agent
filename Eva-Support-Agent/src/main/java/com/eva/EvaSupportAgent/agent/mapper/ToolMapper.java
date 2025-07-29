package com.eva.EvaSupportAgent.agent.mapper;

import com.eva.EvaSupportAgent.agent.model.Tool;
import com.eva.EvaSupportAgent.agent.vo.ToolVO;

public class ToolMapper {

    public static Tool toEntity(ToolVO vo) {
        if (vo == null) {
            return null;
        }

        Tool tool = new Tool();
        tool.setId(vo.getId());
        tool.setType(vo.getType());
        tool.setName(vo.getName());
        tool.setDescription(vo.getDescription());
        tool.setUrl(vo.getUrl());
        tool.setTimeout(vo.getTimeout());
        tool.setParameters(vo.getParameters());
        return tool;
    }

    public static ToolVO toVO(Tool tool) {
        if (tool == null) {
            return null;
        }

        ToolVO vo = new ToolVO();
        vo.setId(tool.getId());
        vo.setType(tool.getType());
        vo.setName(tool.getName());
        vo.setDescription(tool.getDescription());
        vo.setUrl(tool.getUrl());
        vo.setTimeout(tool.getTimeout());
        vo.setParameters(tool.getParameters());
        return vo;
    }
}
