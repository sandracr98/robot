package com.example.robot.infrastructure.controller;

import com.example.robot.application.port.in.ProcessScenarioUseCase;
import com.example.robot.application.port.in.ScenarioResult;
import com.example.robot.infrastructure.dto.ScenarioRequest;
import com.example.robot.infrastructure.dto.ScenarioResponse;
import com.example.robot.infrastructure.mapper.ScenarioWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/robots")
public class RobotController {

    private final ProcessScenarioUseCase useCase;

    public RobotController(ProcessScenarioUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/execute")
    public ResponseEntity<ScenarioResponse> execute(@Valid @RequestBody ScenarioRequest request) {
        ScenarioResult result = useCase.process(ScenarioWebMapper.toCommand(request));
        return ResponseEntity.ok(ScenarioWebMapper.toResponse(result));
    }
}
