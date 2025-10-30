package com.example.robot.domain;


import com.example.robot.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for atomic robot operations:
 * turning left/right, peeking next position, and moving.
 *
 * These tests ensure the Robot aggregate remains simple and SRP-compliant,
 * delegating instruction execution to {@link Navigator}.
 */
public class RobotTest {

    @Test
    void turnLeft_rotates_correctly() {
        Grid grid = new Grid(5, 5);
        Robot robot = new Robot(new Position(0, 0), Orientation.N, grid);

        robot.turnLeft();  // N -> W
        assertEquals(Orientation.W, robot.orientation());

        robot.turnLeft();  // W -> S
        assertEquals(Orientation.S, robot.orientation());

        robot.turnLeft();  // S -> E
        assertEquals(Orientation.E, robot.orientation());

        robot.turnLeft();  // E -> N
        assertEquals(Orientation.N, robot.orientation());
    }

    @Test
    void turnRight_rotates_correctly() {
        Grid grid = new Grid(5, 5);
        Robot robot = new Robot(new Position(0, 0), Orientation.N, grid);

        robot.turnRight(); // N -> E
        assertEquals(Orientation.E, robot.orientation());

        robot.turnRight(); // E -> S
        assertEquals(Orientation.S, robot.orientation());

        robot.turnRight(); // S -> W
        assertEquals(Orientation.W, robot.orientation());

        robot.turnRight(); // W -> N
        assertEquals(Orientation.N, robot.orientation());
    }

    @Test
    void peekNext_returns_position_without_moving_robot() {
        Grid grid = new Grid(5, 5);
        Robot robot = new Robot(new Position(2, 2), Orientation.N, grid);

        Position next = robot.peekNext();
        assertEquals(new Position(2, 3), next);
        assertEquals(new Position(2, 2), robot.position(), "Robot must not move when peeking");
    }

    @Test
    void moveTo_updates_position() {
        Grid grid = new Grid(5, 5);
        Robot robot = new Robot(new Position(2, 2), Orientation.N, grid);

        Position next = new Position(2, 3);
        robot.moveTo(next);

        assertEquals(new Position(2, 3), robot.position());
    }

    @Test
    void constructor_rejects_initial_outside_position() {
        Grid grid = new Grid(5, 5);
        assertThrows(DomainException.class,
                () -> new Robot(new Position(6, 0), Orientation.N, grid));
    }

    @Test
    void null_safety() {
        Grid grid = new Grid(1, 1);

        assertThrows(NullPointerException.class, () -> new Robot(null, Orientation.N, grid));
        assertThrows(NullPointerException.class, () -> new Robot(new Position(0,0), null, grid));
        assertThrows(NullPointerException.class, () -> new Robot(new Position(0,0), Orientation.N, null));
    }
}
