package com.example.robot.application.port.in;

/** App-level DTO: one robot program (initial pose + raw instructions). */
public record RobotProgram(int startX, int startY, char orientation, String instructions) {

}
