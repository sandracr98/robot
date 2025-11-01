package com.example.robot.infrastructure.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

/** DTO representing the entire scenario request: grid size and robot programs. */
public record ScenarioRequest(@Min(0) int maxX,
                              @Min(0) int maxY,
                              @NotNull @Size(min = 1) List<@Valid RobotProgramRequest> programs) {

}

