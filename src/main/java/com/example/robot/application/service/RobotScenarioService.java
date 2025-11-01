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

        List<FinalState> finals = new ArrayList<>(command.programs().size());

        // Robots se procesan SECUENCIALMENTE
        for (RobotProgram p : command.programs()) {
            Robot robot = new Robot(
                    new Position(p.startX(), p.startY()),
                    Orientation.fromChar(p.orientation()),
                    grid
            );
            navigator.apply(robot, InstructionSequence.parse(p.instructions()));
            finals.add(new FinalState(
                    robot.position().x(),
                    robot.position().y(),
                    robot.orientation().asChar()
            ));
        }

        return new ScenarioResult(finals);

    }
}
