package com.example.robot.application.port.in;

import java.util.List;

/** App-level DTO: output for the full scenario. */
public record ScenarioResult(List<FinalState> finals) {
}
