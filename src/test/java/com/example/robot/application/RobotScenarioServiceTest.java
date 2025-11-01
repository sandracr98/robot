package com.example.robot.application;


import com.example.robot.application.port.in.*;
import com.example.robot.application.service.RobotScenarioService;
import com.example.robot.domain.IgnorePolicy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/** Application-level test: verifies orchestration with the sample scenario. */
public class RobotScenarioServiceTest {

    @Test
    void processes_sample_scenario_sequentially() {
        ProcessScenarioUseCase useCase = new RobotScenarioService(new IgnorePolicy());

        ScenarioCommand cmd = new ScenarioCommand(
                new GridSize(5,5),
                List.of(
                        new RobotProgram(1,2,'N',"LMLMLMLMM"),
                        new RobotProgram(3,3,'E',"MMRMMRMRRM")
                )
        );

        ScenarioResult result = useCase.process(cmd);

        assertEquals(2, result.finals().size());
        assertEquals(1, result.finals().get(0).x());
        assertEquals(3, result.finals().get(0).y());
        assertEquals('N', result.finals().get(0).orientation());

        assertEquals(5, result.finals().get(1).x());
        assertEquals(1, result.finals().get(1).y());
        assertEquals('E', result.finals().get(1).orientation());
    }

}
