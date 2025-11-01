package com.example.robot.domain;

/**
 * Interface to manage occupancy of positions in a grid or space.
 */
public interface Occupancy {

    /**
     * Checks if a given position is free (not occupied).
     *
     * @param position the position to check
     * @return true if the position is free, false otherwise
     */
    boolean isFree(Position position);

    /**
     * Marks a given position as occupied.
     *
     * @param position the position to occupy
     */
    void occupy(Position position);

    /**
     * Determines if a move from the current position to the next position is allowed.
     *
     * @param next    the next position to move to
     * @param current the current position
     * @return true if the move is allowed, false if it is blocked due to occupancy
     */
    boolean allowMove(Position next, Position current);



}
