package com.example.robot.infrastructure.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/** DTO representing a robot's program request. */
    public record RobotProgramRequest(@Min(0) int startX,
                                      @Min(0) int startY,
                                      @Pattern(regexp = "^[NESWnesw]$", message = "orientation must be one of N,E,S,W") String orientation,
                                      @NotBlank @Pattern(regexp = "^[LRMlrm]+$", message = "instructions must contain only L,R,M") String instructions
) {

}
