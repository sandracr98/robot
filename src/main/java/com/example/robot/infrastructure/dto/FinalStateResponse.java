package com.example.robot.infrastructure.dto;


/** DTO representing the final state of a robot after executing its instructions. */
public record FinalStateResponse(int x, int y, char orientation) { }

