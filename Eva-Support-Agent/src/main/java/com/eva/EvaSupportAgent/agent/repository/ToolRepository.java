package com.eva.EvaSupportAgent.agent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eva.EvaSupportAgent.agent.model.Tool;

public interface ToolRepository extends JpaRepository<Tool, Long> {
	List<Tool> findByAgentId(Long agentId);

}
