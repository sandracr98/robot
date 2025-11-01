package com.example.robot.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** End-to-end at domain level: Navigator + Robot + IgnorePolicy. */
public class NavigatorTest {

    @Test
    void sample_from_statement_two_robots() {
        Grid grid = new Grid(5, 5);
        Navigator nav = new Navigator(new IgnorePolicy());

        Robot r1 = new Robot(new Position(1, 2), Orientation.N, grid);
        nav.apply(r1, InstructionSequence.parse("LMLMLMLMM"));
        assertEquals(new Position(1, 3), r1.position());
        assertEquals('N', r1.orientation().asChar());

        Robot r2 = new Robot(new Position(3, 3), Orientation.E, grid);
        nav.apply(r2, InstructionSequence.parse("MMRMMRMRRM"));
        assertEquals(new Position(5, 1), r2.position());
        assertEquals('E', r2.orientation().asChar());
    }

    @Test
    void out_of_bounds_move_is_ignored_with_ignore_policy() {
        Grid grid = new Grid(1, 1);
        Navigator nav = new Navigator(new IgnorePolicy());
        Robot r = new Robot(new Position(1, 1), Orientation.N, grid);

        nav.apply(r, InstructionSequence.parse("M")); // outside → ignored
        assertEquals(new Position(1, 1), r.position());
        assertEquals(Orientation.N, r.orientation());
    }
}
