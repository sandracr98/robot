package com.example.robot.domain;

import java.util.Objects;

/**
 * Represents an immutable coordinate within the grid.
 *
 * <p>
 * This type is implemented as a Java {@code record}, which makes it a
 * <strong>value object</strong> by design. Records in Java provide:
 * <ul>
 *     <li>Implicit immutability</li>
 *     <li>Auto-generated constructor and accessors</li>
 *     <li>Well-defined {@code equals}, {@code hashCode}, and {@code toString}</li>
 * </ul>
 * This makes {@code Position} ideal for representing domain values such as
 * grid coordinates, where identity is defined by value, not identity reference.
 * </p>
 *
 * <h2>Domain behavior</h2>
 * <p>
 * The main responsibility of {@code Position} is to model a point in the grid
 * and provide movement operations in a safe and explicit manner.
 * </p>
 *
 * <ul>
 *     <li>{@link #move(Orientation)} returns the next position by applying the orientation vector</li>
 *     <li>Coordinates are never mutated; movement always returns a new instance</li>
 *     <li>Boundary enforcement is intentionally delegated to {@code Grid}</li>
 * </ul>
 *
 * <h2>DDD Context</h2>
 * <p>
 * As a Value Object in the domain layer, {@code Position} contributes to a rich, expressive
 * domain model. It helps ensure navigation logic is safe, explicit, and testable without
 * the risk of accidental state changes.
 * </p>
 */
public record Position(int x, int y) {

    /**
     * Returns a new Position one step ahead in the given orientation.
     *
     * @param o the orientation to apply (must not be null)
     * @return a new Position translated by (dx, dy)
     */
    public Position move(Orientation o) {
        Objects.requireNonNull(o, "orientation must not be null");
        return new Position(x + o.dx, y + o.dy);
    }
}