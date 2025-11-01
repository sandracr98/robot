package com.example.robot.infrastructure.dto;

/** DTO representing a robot's program request. */
    public record RobotProgramRequest(int startX, int startY, String orientation, String instructions) {

}
