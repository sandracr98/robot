package com.example.robot.application.port.in;

/** App-level DTO: final state per robot (for output). */
public record FinalState(int x, int y, char orientation) {
}
