package com.example.robot.infrastructure.mapper;

import com.example.robot.application.port.in.GridSize;
import com.example.robot.application.port.in.RobotProgram;
import com.example.robot.application.port.in.ScenarioCommand;
import com.example.robot.application.port.in.ScenarioResult;
import com.example.robot.infrastructure.dto.FinalStateResponse;
import com.example.robot.infrastructure.dto.ScenarioRequest;
import com.example.robot.infrastructure.dto.ScenarioResponse;

import java.util.List;

/** Mapper converting between web DTOs and app-level DTOs for scenarios. */
public class ScenarioWebMapper {

    private ScenarioWebMapper() {}

    // web → app
    /**
     * Converts a ScenarioRequest DTO from the web layer into a ScenarioCommand for the application layer.
     *
     * @param req the ScenarioRequest DTO containing grid size and robot programs
     * @return a ScenarioCommand representing the scenario to be processed
     */
    public static ScenarioCommand toCommand(ScenarioRequest req) {
        GridSize grid = new GridSize(req.maxX(), req.maxY());
        List<RobotProgram> programs = req.programs().stream()
                .map(p -> new RobotProgram(
                        p.startX(),
                        p.startY(),
                        p.orientation().toUpperCase().charAt(0),
                        p.instructions().toUpperCase()
                ))
                .toList();
        return new ScenarioCommand(grid, programs);
    }

    // app → web
    /**
     * Converts a ScenarioResult from the application layer into a ScenarioResponse DTO for the web layer.
     *
     * @param result the ScenarioResult containing final states of robots
     * @return a ScenarioResponse DTO representing the final states
     */
    public static ScenarioResponse toResponse(ScenarioResult result) {
        List<FinalStateResponse> finals = result.finals().stream()
                .map(f -> new FinalStateResponse(f.x(), f.y(), f.orientation()))
                .toList();
        return new ScenarioResponse(finals);
    }
}
