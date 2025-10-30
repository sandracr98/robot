package com.example.robot.domain;

import java.util.Objects;

/**
 * Domain service that applies a program (sequence of instructions) to a robot,
 * delegating out-of-bounds handling to a strategy (policy).
 */
public final class Navigator {

    private final OutOfBoundsPolicy policy;

    public Navigator(OutOfBoundsPolicy policy) {
        this.policy = Objects.requireNonNull(policy);
    }

    public void apply(Robot robot, InstructionSequence program) {
        Objects.requireNonNull(robot, "robot must not be null");
        Objects.requireNonNull(program, "program must not be null");

        for (Instruction ins : program.asList()) {
            switch (ins) {
                case L -> robot.turnLeft();
                case R -> robot.turnRight();
                case M -> policy.handle(robot, robot.peekNext());
            }
        }
    }

}
