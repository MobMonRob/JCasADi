package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

/**
 * Represents an inequality constraint of the form lhs <= rhs.
 */
public class InequalityConstraint extends Constraint {

    /**
     * Constructs an inequality constraint with the given left-hand side and right-hand side.
     *
     * @param lhs the left-hand side expression.
     * @param rhs the right-hand side expression.
     */
    public InequalityConstraint(MXWrapper lhs, MXWrapper rhs) {
        super(lhs, rhs);
    }

    /**
     * Returns the inequality constraint expression, typically represented as (lhs - rhs).
     *
     * @return an Expression representing the inequality constraint.
     */
    @Override
    public MXWrapper getConstraintExpression() {
        return lhs.subtract(rhs);
    }

    public MXWrapper getInequalityConstraintExpression(String comparisonOperator) {
        if (comparisonOperator.equals("<")) {
            return lhs.lt(rhs);
        } else if (comparisonOperator.equals(">")) {
            return lhs.gt(rhs);
        } else if (comparisonOperator.equals("<=")) {
            return lhs.le(rhs);
        } else if (comparisonOperator.equals(">=")) {
            return lhs.ge(rhs);
        } else {
            throw new UnsupportedOperationException("Unsupported comparison operator: " + comparisonOperator +
                    ". Please only use the following operators: >, <, <= or >=");
        }
    }

    /**
     * Checks if the inequality constraint is feasible by verifying that (lhs - rhs)
     * is less than or equal to the specified tolerance.
     *
     * @param env a map of variable names to their numerical values.
     * @param tolerance the tolerance for feasibility.
     * @return true if (lhs - rhs) <= tolerance, false otherwise.
     */
    @Override
    public boolean isFeasible(DMVector env, double tolerance) {
        // return evaluate(env) <= tolerance;
        return false;
    }

    /**
     * Returns a new InequalityConstraint with both sides scaled by the given factor.
     *
     * @param factor the scaling factor.
     * @return a new scaled InequalityConstraint.
     */
    @Override
    public Constraint scale(double factor) {
        return new InequalityConstraint(lhs.multiply(factor), rhs.multiply(factor));
    }

    public String toString(String comparisonOperator) {
        if (comparisonOperator.equals("<")) {
            return lhs.toString() + " < " + rhs.toString();
        } else if (comparisonOperator.equals(">")) {
            return lhs.toString() + " > " + rhs.toString();
        } else if (comparisonOperator.equals("<=")) {
            return lhs.toString() + " <= " + rhs.toString();
        } else if (comparisonOperator.equals(">=")) {
            return lhs.toString() + " >= " + rhs.toString();
        } else {
            throw new UnsupportedOperationException("Unsupported comparison operator: " + comparisonOperator +
                    ". Please only use the following operators: >, <, <= or >=");
        }
    }

}
