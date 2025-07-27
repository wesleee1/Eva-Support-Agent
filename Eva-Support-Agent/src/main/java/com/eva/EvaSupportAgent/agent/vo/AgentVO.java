package com.eva.EvaSupportAgent.agent.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentVO {
	private String name;
	private String language;
	private String voiceModel;
	private String llmParameters;
}