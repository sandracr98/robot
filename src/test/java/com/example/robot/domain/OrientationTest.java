package com.example.robot.domain;

import com.example.robot.domain.Orientation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for {@link Orientation}.
 *
 * <p>
 * These tests verify the correctness of orientation transitions and utility methods.
 * In the context of the robot navigation domain, orientation is a core primitive,
 * so ensuring correctness in rotations and representation conversions is essential.
 * </p>
 *
 * <p>
 * The tests cover:
 * <ul>
 *     <li>90° left turns</li>
 *     <li>90° right turns</li>
 *     <li>Character ↔ Orientation conversions</li>
 *     <li>Movement vector integrity</li>
 *     <li>Error handling for invalid input</li>
 * </ul>
 * </p>
 */
public class OrientationTest {

    /**
     * Ensures that turning left cycles through N → W → S → E → N.
     * This validates the domain rule for a 90° counterclockwise rotation.
     */
    @Test
    void turnLeft_correctly() {
        assertEquals(Orientation.W, Orientation.N.turnLeft(), "N left → W");
        assertEquals(Orientation.S, Orientation.W.turnLeft(), "W left → S");
        assertEquals(Orientation.E, Orientation.S.turnLeft(), "S left → E");
        assertEquals(Orientation.N, Orientation.E.turnLeft(), "E left → N");
    }

    /**
     * Ensures that turning right cycles through N → E → S → W → N.
     * Validates the 90° clockwise rotation logic.
     */
    @Test
    void turnRight_cycles_correctly() {
        assertEquals(Orientation.E, Orientation.N.turnRight(), "N right → E");
        assertEquals(Orientation.S, Orientation.E.turnRight(), "E right → S");
        assertEquals(Orientation.W, Orientation.S.turnRight(), "S right → W");
        assertEquals(Orientation.N, Orientation.W.turnRight(), "W right → N");
    }

    /**
     * Validates conversion between character representation and enum values.
     * This guarantees that user inputs (e.g., "N") map correctly into domain objects.
     */
    @Test
    void fromChar_and_asChar_are_inverse() {
        assertEquals('N', Orientation.fromChar('N').asChar());
        assertEquals('E', Orientation.fromChar('E').asChar());
        assertEquals('S', Orientation.fromChar('S').asChar());
        assertEquals('W', Orientation.fromChar('W').asChar());

        // lower case support is expected behavior for user-friendly input parsing
        assertEquals(Orientation.N, Orientation.fromChar('n'));
        assertEquals(Orientation.W, Orientation.fromChar('w'));
    }

    /**
     * Ensures that invalid orientation characters result in an exception.
     * Important to enforce domain constraints and avoid silent failures.
     */
    @Test
    void invalid_orientation_throws() {
        assertThrows(IllegalArgumentException.class, () -> Orientation.fromChar('X'));
        assertThrows(IllegalArgumentException.class, () -> Orientation.fromChar(' '));
    }

    /**
     * Verifies that each orientation has the expected movement vector (dx, dy).
     * This is crucial for correct position updates in the movement logic.
     */
    @Test
    void movement_vectors_are_correct() {
        assertEquals(0, Orientation.N.dx);  assertEquals(1,  Orientation.N.dy);
        assertEquals(1, Orientation.E.dx);  assertEquals(0,  Orientation.E.dy);
        assertEquals(0, Orientation.S.dx);  assertEquals(-1, Orientation.S.dy);
        assertEquals(-1,Orientation.W.dx);  assertEquals(0,  Orientation.W.dy);
    }


}
