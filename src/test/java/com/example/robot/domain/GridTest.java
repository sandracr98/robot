package com.example.robot.domain;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Grid}.
 *
 * Ensures grid boundaries are respected and invalid dimensions are rejected.
 */
public class GridTest {

    /** A grid with positive bounds accepts positions inside the rectangle inclusive. */
    @Test
    void inside_returns_true_for_valid_positions() {
        Grid grid = new Grid(5, 5);

        assertTrue(grid.inside(new Position(0, 0)));
        assertTrue(grid.inside(new Position(5, 5)));
        assertTrue(grid.inside(new Position(3, 2)));
    }

    /** Positions outside the boundary are rejected. */
    @Test
    void inside_returns_false_for_out_of_bounds_positions() {
        Grid grid = new Grid(5, 5);

        assertFalse(grid.inside(new Position(-1, 0)));
        assertFalse(grid.inside(new Position(0, -1)));
        assertFalse(grid.inside(new Position(6, 3)));
        assertFalse(grid.inside(new Position(3, 6)));
    }

    /** Null must not be accepted. */
    @Test
    void inside_with_null_position_throws() {
        Grid grid = new Grid(5, 5);
        assertThrows(NullPointerException.class, () -> grid.inside(null));
    }

    /** Dimensions must not be negative. */
    @Test
    void constructor_rejects_negative_bounds() {
        assertThrows(IllegalArgumentException.class, () -> new Grid(-1, 2));
        assertThrows(IllegalArgumentException.class, () -> new Grid(2, -1));
    }
}
