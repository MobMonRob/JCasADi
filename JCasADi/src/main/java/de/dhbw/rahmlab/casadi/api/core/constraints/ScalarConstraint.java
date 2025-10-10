package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

/**
 * Class representing a scalar constraint in an optimization problem.
 * Extends the AbstractConstraint to provide specific implementation for scalar constraints.
 */
public class ScalarConstraint extends AbstractConstraint {

    /**
     * Constructs a ScalarConstraint with specified left-hand side (lhs), comparison operator, and right-hand side (rhs).
     *
     * @param lhs the left-hand side expression
     * @param cmp the comparison operator
     * @param rhs the right-hand side expression
     */
    public ScalarConstraint(MXWrapper lhs, Comparison cmp, MXWrapper rhs) {
        super(lhs, cmp, rhs);
    }

    /**
     * Creates a new instance of ScalarConstraint with specified lhs, comparison operator, and rhs.
     * This method is used to scale the constraint or create a new constraint with modified expressions.
     *
     * @param newLhs the new left-hand side expression
     * @param cmp the comparison operator
     * @param newRhs the new right-hand side expression
     * @return a new instance of ScalarConstraint
     */
    @Override
    protected Constraint create(MXWrapper newLhs, Comparison cmp, MXWrapper newRhs) {
        return new ScalarConstraint(newLhs, cmp, newRhs);
    }

}
