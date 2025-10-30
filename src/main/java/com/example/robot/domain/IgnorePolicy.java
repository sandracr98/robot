package com.example.robot.domain;

public class IgnorePolicy implements OutOfBoundsPolicy{
    @Override
    public void handle(Robot robot, Position next) {
        if (robot.grid().inside(next)) {
            robot.moveTo(next);
        }
    }
}
