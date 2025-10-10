package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.MapStringToDouble;

/**
 * Interface representing a constraint in an optimization problem.
 * Provides methods to evaluate and manipulate constraints.
 */
public interface Constraint {

    /**
     * Returns the pure MX expression that represents the difference between the left-hand side (lhs) and right-hand side (rhs).
     *
     * @return an instance of MXWrapper representing the expression (lhs - rhs)
     */
    MXWrapper getExpression();

    /**
     * Determines if the constraint is feasible under a given variable assignment and tolerance.
     * Returns true if the absolute difference between lhs and rhs is less than or equal to the specified tolerance.
     *
     * @param env the variable assignment as a MapStringToDouble
     * @param tolerance the tolerance level
     * @return true if the constraint is feasible, false otherwise
     */
    boolean isFeasible(MapStringToDouble env, double tolerance);

    /**
     * Returns a new instance of Constraint where both lhs and rhs are scaled by the specified factor.
     *
     * @param factor the scaling factor
     * @return a new scaled Constraint instance
     */
    Constraint scaledBy(double factor);

    /**
     * Returns the comparison operator used in the constraint.
     *
     * @return the Comparison instance representing the operator
     */
    Comparison getComparison();

    /**
     * Returns a string representation of the constraint, such as "x^2 + 1 <= y".
     *
     * @return a string representing the constraint
     */
    @Override
    String toString();

    /**
     * Parses a string representation of a constraint, such as "x^2 + 1 <= y" or "x = y + 2",
     * and automatically returns the appropriate Equality or Inequality instance.
     *
     * @param s the string representation of the constraint
     * @return a Constraint instance parsed from the string
     */
    static Constraint parse(String s) {
        return ConstraintBuilder.parse(s).build();
    }

}
