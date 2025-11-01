package com.example.robot.infrastructure.config;

import com.example.robot.application.port.in.ProcessScenarioUseCase;
import com.example.robot.application.service.RobotScenarioService;
import com.example.robot.domain.IgnorePolicy;
import com.example.robot.domain.OutOfBoundsPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationWiring {

    @Bean
    public OutOfBoundsPolicy outOfBoundsPolicy() {
        return new IgnorePolicy();
    }

    @Bean
    public ProcessScenarioUseCase processScenarioUseCase(OutOfBoundsPolicy policy) {
        return new RobotScenarioService(policy);
    }
}
