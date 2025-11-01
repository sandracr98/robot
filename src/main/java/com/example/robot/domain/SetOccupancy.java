package com.example.robot.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Implementation of the Occupancy interface using a Set to track occupied positions.
 */
public class SetOccupancy implements Occupancy{

    /** Set to store occupied positions */
    private final Set<Position> occupiedPositions = new HashSet<>();

    public SetOccupancy() {
    }

    /**
     * Constructor that initializes the occupancy with a collection of occupied positions.
     *
     * @param initial the initial collection of occupied positions
     */
    public SetOccupancy(Collection<Position> initial)  {
        if (initial != null)
            occupiedPositions.addAll(initial);
    }

    /**
     * Checks if a given position is free (not occupied).
     *
     * @param position the position to check
     * @return true if the position is free, false otherwise
     */
    @Override
    public boolean isFree(Position position) {
        return !occupiedPositions.contains(Objects.requireNonNull(position));
    }


    /**
     * Marks a given position as occupied.
     *
     * @param position the position to occupy
     */
    @Override
    public void occupy(Position position) {
        occupiedPositions.add(Objects.requireNonNull(position));
    }
}
