package com.example.robot.infrastructure.dto;

import java.util.List;


/** DTO representing the scenario response containing final states of robots. */
public record ScenarioResponse(List<FinalStateResponse> finals) { }

