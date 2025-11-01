package com.example.robot.domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class InstructionSequencyTest {

    @Test
    void parse_valid_string_into_list() {
        InstructionSequence seq = InstructionSequence.parse("LrMm");
        assertEquals(List.of(Instruction.L, Instruction.R, Instruction.M, Instruction.M), seq.asList());
    }

    @Test
    void parse_throws_on_invalid_char() {
        assertThrows(IllegalArgumentException.class, () -> InstructionSequence.parse("LMX"));
    }

    @Test
    void of_accepts_existing_list() {
        InstructionSequence seq = InstructionSequence.of(List.of(Instruction.L, Instruction.M));
        assertEquals(2, seq.asList().size());
    }
}
