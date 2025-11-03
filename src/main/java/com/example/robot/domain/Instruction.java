package com.example.robot.domain;



/**
 * Represents a single domain instruction for the robot.
 *
 * <p>This enum models the three valid movement commands:</p>
 * <ul>
 *     <li>{@code L} - turn left 90°</li>
 *     <li>{@code R} - turn right 90°</li>
 *     <li>{@code M} - move forward one grid unit</li>
 * </ul>
 *
 * <h2>Domain context</h2>
 * <p>
 * As part of the ubiquitous language, {@code Instruction} ensures
 * navigation commands are expressed explicitly and type-safely in the domain
 * rather than using raw characters. This promotes clarity and prevents invalid
 * instructions from propagating through the system.
 * </p>
 *
 * <h2>Validation</h2>
 * <p>
 * The factory {@link #fromChar(char)} converts raw input into a valid domain
 * instruction, rejecting unexpected characters and enforcing correctness at
 * the boundaries of the model.
 * </p>
 */
public enum Instruction {
    L, R, M;


    /**
     * Parses a raw character into a valid {@code Instruction}.
     *
     * @param c the character representing the instruction
     * @return the corresponding {@code Instruction}
     * @throws IllegalArgumentException if the character does not match L, R, or M
     */
    public static Instruction fromChar(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'L' -> L;
            case 'R' -> R;
            case 'M' -> M;
            default -> throw new IllegalArgumentException("Invalid instruction: " + c);
        };
    }
}