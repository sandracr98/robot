package com.example.robot.domain;


import java.util.Objects;

/**
 * Represents the robot aggregate root in the domain model.
 *
 * <p>
 * This type owns the robot's mutable state (position and orientation)
 * and exposes atomic domain behaviors such as turning and advancing.
 * It deliberately does <strong>not</strong> parse or interpret instruction strings;
 * orchestration and policies live in {@code Navigator}.
 * </p>
 *
 * <h2>DDD context</h2>
 * <ul>
 *   <li>Aggregate Root controlling robot state and invariants</li>
 *   <li>Enforces valid initial state (inside grid)</li>
 *   <li>Delegates navigation rules to {@code Navigator}</li>
 *   <li>Works alongside immutable Value Objects like {@code Position}</li>
 * </ul>
 *
 * <p>
 * Mutability here is intentional and controlled: aggregate roots in DDD
 * may evolve over time to reflect actions within the domain.
 * </p>
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
