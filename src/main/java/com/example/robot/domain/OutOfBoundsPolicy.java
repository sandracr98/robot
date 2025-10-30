package com.example.robot.domain;

public interface OutOfBoundsPolicy {
    void handle(Robot robot, Position next);

}
