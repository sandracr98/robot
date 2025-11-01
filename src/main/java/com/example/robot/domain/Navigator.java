package com.example.robot.domain;

import java.util.Objects;

/**
 * Domain service that applies a program (sequence of instructions) to a robot,
 * delegating out-of-bounds handling to a strategy (policy).
 */
public final class Navigator {

    private final OutOfBoundsPolicy policy;

    /**
     * Constructs a Navigator with the given out-of-bounds policy.
     *
     * @param policy the out-of-bounds policy to use
     * @throws NullPointerException if policy is null
     */
    public Navigator(OutOfBoundsPolicy policy) {
        this.policy = Objects.requireNonNull(policy);
    }

    /**
     * Applies a sequence of instructions to the given robot.
     *
     * @param robot   the robot to control
     * @param program the sequence of instructions to apply
     * @throws NullPointerException if robot or program is null
     */
    public void apply(Robot robot, InstructionSequence program) {
        apply(robot, program, null, false);
    }

    /**
     * Applies a sequence of instructions to the given robot, considering occupancy.
     *
     * @param robot        the robot to control
     * @param program      the sequence of instructions to apply
     * @param occupancy    the occupancy manager (can be null)
     * @param occupyFinal whether to occupy the final position
     * @throws NullPointerException if robot or program is null
     */
    public void apply(Robot robot, InstructionSequence program, Occupancy occupancy, boolean occupyFinal) {

        // Validate input parameters
        // Throw NullPointerException if robot or program is null
        Objects.requireNonNull(robot, "robot must not be null");
        Objects.requireNonNull(program, "program must not be null");

        // Process each instruction in the program
        // For each instruction, update the robot's state accordingly
        // Handle out-of-bounds and occupancy as specified
        for (Instruction ins : program.asList()) {
            switch (ins) {
                case L -> robot.turnLeft();
                case R -> robot.turnRight();
                case M -> {
                    Position next = robot.peekNext();

                    // Check bounds and occupancy before moving
                    // Handle out-of-bounds according to policy
                    if (!robot.grid().inside(next)) {
                        policy.handle(robot, next); // p. ej., ignorar
                        continue;
                    }

                    // Check occupancy if provided
                    // Skip move if the next position is occupied
                    if (occupancy != null && !occupancy.isFree(next)) {
                        // position occupied, skip move
                        continue;
                    }

                    // Move the robot to the next position
                    // This line is reached only if the move is valid
                    robot.moveTo(next);
                }
            }
        }

        // Occupy the final position if required
        // This is done after all instructions have been processed

        if (occupancy != null && occupyFinal) {
            occupancy.occupy(robot.position());
        }
    }


}
