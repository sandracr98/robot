package com.example.robot.domain;


/**
 * Represents the cardinal orientation of the robot within the grid.
 * <p>
 * This enum models the four possible facing directions: North (N), East (E),
 * South (S) and West (W). Each direction has an associated movement vector
 * (dx, dy), which is used to compute the robot's next position when moving
 * forward one unit.
 * </p>
 *
 * <h2>Domain behavior</h2>
 * <ul>
 *   <li>{@link #turnLeft()} rotates the robot 90° counterclockwise</li>
 *   <li>{@link #turnRight()} rotates the robot 90° clockwise</li>
 *   <li>{@link #fromChar(char)} converts character input into an Orientation</li>
 *   <li>{@link #asChar()} returns the compact character representation</li>
 * </ul>
 *
 * <h2>DDD Context</h2>
 * <p>
 * In the Domain-Driven Design model, this enum is part of the domain layer.
 * It encodes core business rules related to orientation changes and movement direction,
 * ensuring that the robot's navigation logic is expressed explicitly and safely.
 * </p>
 *
 * <p>
 * This type is deliberately immutable, ensuring safe usage as a value in the domain layer.
 * </p>
 */
public enum Orientation {
    N(0, 1), E(1, 0), S(0, -1), W(-1, 0);

    /** Horizontal movement delta */
    public final int dx;

    /** Vertical movement delta */
    public final int dy;

    Orientation(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the orientation resulting from a 90° clockwise turn.
     *
     * @return the new orientation after turning right
     */
    public Orientation turnRight() {
        return switch (this) {
            case N -> E;
            case S -> W;
            case E -> S;
            case W -> N;
        };
    }


    /**
     * Returns the orientation resulting from a 90° counter-clockwise turn.
     *
     * @return the new orientation after turning left
     */
    public Orientation turnLeft() {
        return switch (this) {
            case N -> W;
            case S -> E;
            case E -> N;
            case W -> S;
        };
    }


    /**
     * Converts a single character input into its corresponding orientation value.
     *
     * @param c a character representing the direction (N, E, S, W)
     * @return the Orientation represented by the given character
     * @throws IllegalArgumentException if the character does not match any direction
     */
    public static Orientation fromChar(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'N' -> N; case 'E' -> E; case 'S' -> S; case 'W' -> W;
            default -> throw new IllegalArgumentException("Invalid orientation: " + c);
        };
    }

    /**
     * Returns the character representation (N, E, S, W) of this orientation.
     *
     * @return the orientation as a single uppercase character
     */
    public char asChar() {
        return switch (this) {
            case N -> 'N'; case E -> 'E'; case S -> 'S'; case W -> 'W';
        };
    }
}
