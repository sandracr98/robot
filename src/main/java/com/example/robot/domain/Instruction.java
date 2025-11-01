package com.example.robot.domain;



/** Domain instruction: Left, Right, Move. */

public enum Instruction {
    L, R, M;

    public static Instruction fromChar(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'L' -> L;
            case 'R' -> R;
            case 'M' -> M;
            default -> throw new IllegalArgumentException("Invalid instruction: " + c);
        };
    }
}