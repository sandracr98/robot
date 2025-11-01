package com.example.robot.domain;


import java.util.Objects;

/**
 * Aggregate root holding state (position, orientation) and atomic movement operations.
 * Does not interpret instruction strings: orchestration lives in Navigator.
 */
public final class Robot {

    private Position position;
    private Orientation orientation;
    private Grid grid;



    /** 90º counterclockwise rotation. */
    public void turnLeft() { orientation = orientation.turnLeft(); }

    /** 90º clockwise rotation. */
    public void turnRight() { orientation = orientation.turnRight(); }

    /** Compute the next position if moving forward (does not mutate). */
    public Position peekNext() { return position.move(orientation); }

    /** Move to a given position (assumes caller enforces policies). */
    public void moveTo(Position next) { this.position = next; }


    public Robot(Position position, Orientation orientation, Grid grid) {
        this.position = Objects.requireNonNull(position, "start must not be null");
        this.orientation = Objects.requireNonNull(orientation, "orientation must not be null");
        this.grid = Objects.requireNonNull(grid, "grid must not be null");

        if (!grid.inside(position)) {
            throw new com.example.robot.domain.exception.DomainException(
                    "Initial position is outside the grid: " + position
            );
        }
    }

    public Position position() { return position; }
    public Orientation orientation() { return orientation; }
    public Grid grid() { return grid; }
}
