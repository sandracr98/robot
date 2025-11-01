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
}
