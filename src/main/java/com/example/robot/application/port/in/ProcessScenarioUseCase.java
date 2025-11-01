package com.example.robot.application.port.in;

/** Application use case: process a whole scenario (grid + robots). */
public interface ProcessScenarioUseCase {
    ScenarioResult process(ScenarioCommand command);
}
