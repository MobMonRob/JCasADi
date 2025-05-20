package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.MapStringToDMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.MapStringToDouble;

import java.util.Map;

/**
 * Abstract class representing a general constraint in an optimization problem.
 * It encapsulates a left-hand side (LHS) and a right-hand side (RHS) expression.
 */
public abstract class AbstractConstraint implements Constraint {

    /** The left-hand side expression of the constraint. */
    protected final MXWrapper lhs;

    /** The right-hand side expression of the constraint. */
    protected final MXWrapper rhs;

    /** The comparison operator used to evaluate the constraint. */
    protected final Comparison cmp;

    /**
     * Constructs an AbstractConstraint with specified LHS, comparison operator, and RHS.
     *
     * @param lhs the left-hand side expression
     * @param cmp the comparison operator
     * @param rhs the right-hand side expression
     */
    protected AbstractConstraint(MXWrapper lhs, Comparison cmp, MXWrapper rhs) {
        this.lhs = lhs;
        this.cmp = cmp;
        this.rhs = rhs;
    }

    /**
     * Returns the expression representing the difference between LHS and RHS.
     *
     * @return the expression as an MXWrapper
     */
    @Override
    public MXWrapper getExpression() {
        return lhs.subtract(rhs);
    }

    /**
     * Checks if the constraint is feasible given a variable assignment and tolerance.
     *
     * @param assignment the variable assignment
     * @param tol the tolerance level
     * @return true if the constraint is feasible, false otherwise
     */
    @Override
    public boolean isFeasible(MapStringToDouble assignment, double tol) {
        MXVector vars = getExpression().symvar();
        FunctionWrapper fw = new FunctionWrapper(
                "feasCheck",
                vars,
                new MXVector(getExpression())
        );
        MapStringToDMWrapper res = fw.call(assignment.getWrapper());
        MapStringToDouble res1 = new MapStringToDouble(res);

        for (Map.Entry<String, Double> entry : res1.entrySet()) {
            String varName = entry.getKey();
            Double wrapper = entry.getValue();

            switch (cmp) {
                case EQ:
                    if (Math.abs(wrapper) == tol) return false;
                    break;
                case LE:
                    if (Math.abs(wrapper) > tol) return false;
                    break;
                case LT:
                    if (Math.abs(wrapper) >= tol) return false;
                    break;
                case GE:
                    if (Math.abs(wrapper) < -tol) return false;
                    break;
                case GT:
                    if (Math.abs(wrapper) <= -tol) return false;
                    break;
                default:
                    throw new IllegalStateException("Unknown comparison: " + cmp);
            }
        }
        return true;
    }

    /**
     * Scales the constraint by a given factor.
     *
     * @param factor the scaling factor
     * @return a new Constraint scaled by the factor
     */
    @Override
    public Constraint scaledBy(double factor) {
        return create(lhs.multiply(factor), cmp, rhs.multiply(factor));
    }

    /**
     * Factory method to create a specific subclass of Constraint based on the comparison operator.
     *
     * @param newLhs the new left-hand side expression
     * @param cmp the comparison operator
     * @param newRhs the new right-hand side expression
     * @return a new Constraint instance
     */
    protected abstract Constraint create(MXWrapper newLhs, Comparison cmp, MXWrapper newRhs);

    /**
     * Returns the comparison operator of the constraint.
     *
     * @return the comparison operator
     */
    public Comparison getComparison() {
        return this.cmp;
    }

    /**
     * Returns a string representation of the constraint.
     *
     * @return a string representing the constraint
     */
    @Override
    public String toString() {
        return lhs + " " + cmp.symbol() + " " + rhs;
    }

}
