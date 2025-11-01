package com.example.robot.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link PositionTest}.
 *
 * <p>
 * These tests verify the immutability and the movement behavior of the value object.
 * We explicitly keep bounds validation out of this type (handled by {@link Grid}).
 * </p>
 */
public class PositionTest {


    /** Moving one step in each orientation must add the correct (dx, dy). */
    @Test
    void move_applies_orientation_vector() {
        Position p = new Position(2, 3);

        assertEquals(new Position(2, 4), p.move(Orientation.N), "N: y+1");
        assertEquals(new Position(3, 3), p.move(Orientation.E), "E: x+1");
        assertEquals(new Position(2, 2), p.move(Orientation.S), "S: y-1");
        assertEquals(new Position(1, 3), p.move(Orientation.W), "W: x-1");
    }

    /** Position must be immutable: calling move() returns a NEW instance and does not change the original. */
    @Test
    void move_returns_new_instance_and_does_not_mutate() {
        Position original = new Position(0, 0);
        Position moved = original.move(Orientation.N);

        assertEquals(new Position(0, 1), moved);
        assertEquals(new Position(0, 0), original, "original must remain unchanged");
        assertNotSame(original, moved, "defensive: different instances");
    }

    /** Chaining moves should work predictably thanks to immutability. */
    @Test
    void chaining_moves_is_composable() {
        Position p = new Position(1, 1);
        Position after = p
                .move(Orientation.N) // (1,2)
                .move(Orientation.E) // (2,2)
                .move(Orientation.E) // (3,2)
                .move(Orientation.S) // (3,1)
                .move(Orientation.W); // (2,1)
        assertEquals(new Position(2, 1), after);
    }

    /** Equality is structural (record semantics): same x and y → equals & same hashCode. */
    @Test
    void equality_is_structural() {
        Position a = new Position(5, -2);
        Position b = new Position(5, -2);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    /** Null orientation must throw NPE as per contract (fail fast). */
    @Test
    void move_with_null_orientation_throws_npe() {
        Position p = new Position(0, 0);
        assertThrows(NullPointerException.class, () -> p.move(null));
    }
}
