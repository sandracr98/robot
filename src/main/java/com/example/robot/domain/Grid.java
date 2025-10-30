package com.example.robot.domain;


import java.util.Objects;

/**
 * Represents the rectangular area in which the robot can move.
 * The lower-left corner is implicitly (0,0) and the upper-right corner is (maxX, maxY).
 *
 * This type encapsulates the boundary rules of the domain:
 * positions must stay within inclusive grid limits.
 *
 * <p>Validation note:</p>
 * Grid dimensions must be non-negative. Bounds checking happens here,
 * not in {@link Position}, keeping responsibilities separated.
 */
public record Grid(int maxX, int maxY){

    /**
     * Canonical constructor performs validation.
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
