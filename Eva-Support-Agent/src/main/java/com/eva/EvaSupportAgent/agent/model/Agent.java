package com.eva.EvaSupportAgent.agent.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String language;
	private String voiceModel;

	@Column(columnDefinition = "TEXT")
	private String llmParameters;

	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	private String firstMessage;
	private String systemPrompt;

	@OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tool> tools = new ArrayList<>();
}
