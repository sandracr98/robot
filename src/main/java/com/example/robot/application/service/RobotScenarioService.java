package com.example.robot.application.service;


import com.example.robot.application.port.in.*;
import com.example.robot.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Application service: orchestrates domain components to process a scenario.
 * KISS/SOLID: no business rules here; only coordination and mapping.
 */
public final class RobotScenarioService implements ProcessScenarioUseCase {


    private final Navigator navigator;
    private final OutOfBoundsPolicy policy;

    public RobotScenarioService(OutOfBoundsPolicy policy) {
        this.policy = Objects.requireNonNull(policy);
        this.navigator = new Navigator(policy);
    }

    @Override
    public ScenarioResult process(ScenarioCommand command) {

        Objects.requireNonNull(command, "command must not be null");

        GridSize gridSize = command.grid();
        Grid grid = new Grid(gridSize.maxX(), gridSize.maxY());

        Occupancy occupancy = new SetOccupancy();

        List<FinalState> finals = new ArrayList<>(command.programs().size());

        // Process each robot program
        // For each program, create a robot, apply the instructions, and record the final state
        // Return the scenario result with all final states
        for (RobotProgram p : command.programs()) {
            Robot robot = new Robot(
                    new Position(p.startX(), p.startY()),
                    Orientation.fromChar(p.orientation()),
                    grid
            );

            navigator.apply(robot, InstructionSequence.parse(p.instructions()), occupancy, true);

            finals.add(new FinalState(
                    robot.position().x(),
                    robot.position().y(),
                    robot.orientation().asChar()
            ));
        }

        return new ScenarioResult(finals);

    }
}
