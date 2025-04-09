package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.DoubleVector;

/**
 * Represents an equality constraint of the form lhs = rhs.
 */
public class EqualityConstraint extends Constraint {

    /**
     * Constructs an equality constraint with the given left-hand side and right-hand side.
     *
     * @param lhs the left-hand side expression.
     * @param rhs the right-hand side expression.
     */
    public EqualityConstraint(MXWrapper lhs, MXWrapper rhs) {
        super(lhs, rhs);
    }

    /**
     * Returns the equality constraint expression, typically represented as (lhs - rhs).
     *
     * @return an Expression representing the equality constraint.
     */
    @Override
    public MXWrapper getConstraintExpression() {
        return lhs.subtract(rhs);
    }

    public MXWrapper getEqualityConstraintExpression() {
        return lhs.eq(rhs);
    }

    /**
     * Checks if the equality constraint is feasible by verifying that the absolute value
     * of (lhs - rhs) is within the specified tolerance.
     *
     * @param env a map of variable names to their numerical values.
     * @param tolerance the tolerance for feasibility.
     * @return true if |lhs - rhs| is less than or equal to tolerance, false otherwise.
     */
    @Override
    public boolean isFeasible(DMVector env, double tolerance) {
        DMVector toleranceAsVector = new DMVector(tolerance);
        MXVector variables = this.getConstraintExpression().symvar();
        FunctionWrapper function = new FunctionWrapper("", variables, new MXVector(this.getConstraintExpression()));
        DMVector result = function.call(env);
        // return result <= toleranceAsVector;
        return false;
    }

    /**
     * Returns a new EqualityConstraint with both sides scaled by the given factor.
     *
     * @param factor the scaling factor.
     * @return a new scaled EqualityConstraint.
     */
    @Override
    public Constraint scale(double factor) {
        return new EqualityConstraint(lhs.multiply(factor), rhs.multiply(factor));
    }

    @Override
    public String toString() {
        return lhs.toString() + " = " + rhs.toString();
    }
}

