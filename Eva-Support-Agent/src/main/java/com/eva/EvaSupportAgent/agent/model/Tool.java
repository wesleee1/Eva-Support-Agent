package com.eva.EvaSupportAgent.agent.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tool {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String type; // END_CALL or CUSTOM_FUNCTION

	private String name;
	private String description;

	// For Custom Functions
	private String url;
	private Integer timeout; // Optional
	private String parameters; // JSON Schema

	@ManyToOne
	@JoinColumn(name = "agent_id")
	private Agent agent;
}
