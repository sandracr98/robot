package com.example.robot.domain;


import java.util.Objects;

/**
        * Represents the rectangular area in which the robot can operate.
        *
        * <p>This is a Java {@code record}, making {@code Grid} an immutable
        * <strong>Value Object</strong> in the domain model.
        * It defines the upper bounds of the workspace, with (0,0) as the implicit
        * lower-left corner and ({@code maxX}, {@code maxY}) as the upper-right corner.</p>
        *
        * <h2>Domain role</h2>
        * <ul>
 *   <li>Encapsulates boundary rules of the robot's environment</li>
        *   <li>Ensures grid dimensions are valid upon creation</li>
        *   <li>Provides boundary checking via {@link #inside(Position)}</li>
        *   <li>Supports a rich domain model by embedding business constraints</li>
        * </ul>
        *
        * <p>
 * Boundary validation lives here instead of {@code Position},
        * reinforcing <strong>Single Responsibility</strong> and ensuring each
        * Value Object owns the rules relevant to its concept.
        * </p>
        */
public record Grid(int maxX, int maxY){

    /**
     * Canonical constructor performs validation.
     * It guarantees that grid dimensions are non-negative.
     *
     * @throws IllegalArgumentException if maxX or maxY is negative
     */
    public Grid {
        if (maxX < 0 || maxY < 0) {
            throw new IllegalArgumentException("Grid dimensions must be >= 0");
        }
    }

    /**
     * Determines whether a given position lies inside the grid boundaries.
     * Boundaries are inclusive.
     *
     * @param p the position to validate (must not be null)
     * @return true if inside the grid, false otherwise
     * @throws NullPointerException if p is null
     */
    public boolean inside(Position p) {
        Objects.requireNonNull(p, "position must not be null");
        return p.x() >= 0 && p.y() >= 0 && p.x() <= maxX && p.y() <= maxY;
    }


}
