package com.eva.EvaSupportAgent.agent.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolVO {
	private Long id;
	private String type;
	private String name;
	private String description;

	// For Custom Function Type
	private String url;
	private Integer timeout;
	private String parameters;
}
