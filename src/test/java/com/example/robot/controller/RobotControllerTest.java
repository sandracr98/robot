package com.example.robot.controller;

import com.example.robot.application.port.in.FinalState;
import com.example.robot.application.port.in.ProcessScenarioUseCase;
import com.example.robot.application.port.in.ScenarioResult;
import com.example.robot.infrastructure.controller.RobotController;
import com.example.robot.infrastructure.dto.RobotProgramRequest;
import com.example.robot.infrastructure.dto.ScenarioRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RobotController.class)
class RobotControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockitoBean
    ProcessScenarioUseCase useCase;

    @Test
    void execute_returns_expected_response() throws Exception {
        // Mock de respuesta del caso de uso
        ScenarioResult mocked = new ScenarioResult(List.of(
                new FinalState(1,3,'N'),
                new FinalState(5,1,'E')
        ));
        when(useCase.process(any())).thenReturn(mocked);

        ScenarioRequest req = new ScenarioRequest(
                5,5,
                List.of(
                        new RobotProgramRequest(1,2,"N","LMLMLMLMM"),
                        new RobotProgramRequest(3,3,"E","MMRMMRMRRM")
                )
        );

        mvc.perform(post("/api/v1/robots/execute")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finals[0].x").value(1))
                .andExpect(jsonPath("$.finals[0].y").value(3))
                .andExpect(jsonPath("$.finals[0].orientation").value("N"))
                .andExpect(jsonPath("$.finals[1].x").value(5))
                .andExpect(jsonPath("$.finals[1].y").value(1))
                .andExpect(jsonPath("$.finals[1].orientation").value("E"));
    }
}
