package com.example.robot.infrastructure.dto;

import java.util.List;

/** DTO representing the entire scenario request: grid size and robot programs. */
public record ScenarioRequest(int maxX, int maxY, List<RobotProgramRequest> programs) {

}

