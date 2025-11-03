package com.example.robot.domain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents an immutable, validated sequence of movement instructions.
 *
 * <p>This class is a <strong>Value Object</strong> in the domain model.
 * Its responsibility is to ensure that robot instructions are parsed and stored
 * in a safe and expressive form, avoiding raw Strings in the core domain.</p>
 *
 * <h2>Domain motivation</h2>
 * <ul>
 *     <li>Encapsulates the parsing and validation of instruction input</li>
 *     <li>Prevents invalid commands from entering the execution flow</li>
 *     <li>Ensures immutability and safe iteration</li>
 *     <li>Keeps {@code Navigator} focused purely on behavior, not parsing</li>
 * </ul>
 *
 * <p>
 * By handling raw input conversion here, the domain remains clean and the
 * behavior for instruction execution is centralized in navigation logic.
 * </p>
 */
public class InstructionSequence {

    private final List<Instruction> value;

    private InstructionSequence(List<Instruction> value) {
        this.value = List.copyOf(value);
    }

    /**
     * Creates a new sequence from a pre-validated list.
     *
     * @param list list of instructions (non-null)
     */
    public static InstructionSequence of(List<Instruction> list) {
        Objects.requireNonNull(list, "instructions must not be null");
        return new InstructionSequence(list);
    }

    /**
     * Parses a raw string (e.g. "LMLMR") into validated instructions.
     *
     * @param raw non-null string of instruction characters
     * @return a validated {@code InstructionSequence}
     * @throws IllegalArgumentException if invalid characters are found
     */
    public static InstructionSequence parse(String raw) {
        Objects.requireNonNull(raw, "raw instructions must not be null");
        List<Instruction> list = new ArrayList<>(raw.length());
        for (char c : raw.toCharArray()) list.add(Instruction.fromChar(c));
        return new InstructionSequence(list);
    }



    /**
     * Returns the internal list of instructions as an unmodifiable view.
     */
    public List<Instruction> asList() {
        return Collections.unmodifiableList(value);
    }
}
