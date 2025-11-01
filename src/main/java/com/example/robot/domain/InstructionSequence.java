package com.example.robot.domain;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Value object representing a validated, immutable list of instructions.
 * Parsing strings belongs here to keep Navigator simple and focused.
 */
public class InstructionSequence {

    private final List<Instruction> value;

    private InstructionSequence(List<Instruction> value) {
        this.value = List.copyOf(value);
    }

    public static InstructionSequence of(List<Instruction> list) {
        Objects.requireNonNull(list, "instructions must not be null");
        return new InstructionSequence(list);
    }

    /** Factory that parses a raw string like "LMLMR" into instructions, validating characters. */
    public static InstructionSequence parse(String raw) {
        Objects.requireNonNull(raw, "raw instructions must not be null");
        List<Instruction> list = new ArrayList<>(raw.length());
        for (char c : raw.toCharArray()) list.add(Instruction.fromChar(c));
        return new InstructionSequence(list);
    }

    public List<Instruction> asList() {
        return Collections.unmodifiableList(value);
    }
}
