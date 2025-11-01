package com.example.robot.application.port.in;

import java.util.List;

/** App-level DTO: whole scenario to process (grid + programs). */
public record ScenarioCommand(GridSize grid, List<RobotProgram> programs) {

}
