package com.example.robot.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class NavigatiorCollisionTest {

    @Test
    void prevents_entering_occupied_final_cell() {
        Grid grid = new Grid(5,5);
        Navigator nav = new Navigator(new IgnorePolicy());
        Occupancy occ = new SetOccupancy();

        // Robot moves to (1,3)
        // Occupies (1,3)
        Robot a = new Robot(new Position(1,2), Orientation.N, grid);
        nav.apply(a, InstructionSequence.parse("LMLMLMLMM"), occ, true);
        assertEquals(new Position(1,3), a.position());

        // Then, another robot tries to move to (1,3) but can't because it's occupied.
        Robot b = new Robot(new Position(1,2), Orientation.N, grid);
        nav.apply(b, InstructionSequence.parse("MM"), occ, true);
        // Should remain at (1,2)
        // The second robot cannot move into the occupied cell (1,3)
        // So it should stay at (1,2)
        assertEquals(new Position(1,2), b.position());
    }

    @Test
    void robot_waits_when_cell_is_occupied() {
        Grid grid = new Grid(5, 5);
        Navigator nav = new Navigator(new IgnorePolicy());
        Occupancy occupancy = new SetOccupancy();

        // Robot A ocupa (1,3)
        Robot a = new Robot(new Position(1, 2), Orientation.N, grid);
        nav.apply(a, InstructionSequence.parse("M"), occupancy, true);
        assertEquals(new Position(1, 3), a.position());

        // Robot B intenta avanzar a (1,3) pero espera
        Robot b = new Robot(new Position(1, 2), Orientation.N, grid);
        nav.apply(b, InstructionSequence.parse("MM"), occupancy, true);

        // Con "wait": no se mueve nunca
        assertEquals(new Position(1, 2), b.position());
    }
}
