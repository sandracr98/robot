package com.example.robot.domain;

import java.util.Objects;

/**
 * Immutable value object representing a coordinate (x, y) on the grid.
 */
public record Position(int x, int y) {

    /**
     * Returns a new Position one step ahead in the given orientation.
     *
     * @param o the orientation to apply (must not be null)
     * @return a new Position translated by (dx, dy)
     */
    public Position move(Orientation o) {
        Objects.requireNonNull(o, "orientation must not be null");
        return new Position(x + o.dx, y + o.dy);
    }
}