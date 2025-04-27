package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.MapStringToDMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.MapStringToDouble;

import java.util.Map;

/**
 * Abstract class representing a general constraint in an optimization problem.
 * It encapsulates a left-hand side (LHS) and a right-hand side (RHS) expression.
 */
public abstract class AbstractConstraint implements Constraint {

    protected final MXWrapper lhs;
    protected final MXWrapper rhs;
    protected final Comparison cmp;

    protected AbstractConstraint(MXWrapper lhs, Comparison cmp, MXWrapper rhs) {
        this.lhs = lhs;
        this.cmp = cmp;
        this.rhs = rhs;
    }

    @Override
    public MXWrapper getExpression() {
        // immer (lhs – rhs), der Vergleich selbst wird beim Solver verwendet
        return lhs.subtract(rhs);
    }

    @Override
    public boolean isFeasible(MapStringToDouble assignment, double tol) {
        // 1) Alle Variablen im Constraint-Ausdruck ermitteln
        MXVector vars = getExpression().symvar();
        // 2) CasADi-Funktion bauen und ausführen
        FunctionWrapper fw = new FunctionWrapper(
                "feasCheck",
                vars,
                new MXVector(getExpression())
        );
        MapStringToDMWrapper res = fw.call(assignment.getWrapper());
        MapStringToDouble res1 = new MapStringToDouble(res);

        // 3) Elementweise prüfen
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

    @Override
    public Constraint scaledBy(double factor) {
        return create(lhs.multiply(factor), cmp, rhs.multiply(factor));
    }

    /** Factory-Methode, die je nach cmp die richtige Subklasse liefert. */
    protected abstract Constraint create(MXWrapper newLhs, Comparison cmp, MXWrapper newRhs);

    @Override
    public String toString() {
        return lhs + " " + cmp.symbol() + " " + rhs;
    }
//
//    /**
//     * Factory method to create a Constraint from a string.
//     * The string should be in the form "lhs = rhs" for equality constraints or "lhs <= rhs" for inequality constraints.
//     *
//     * @param constraintStr the string representation of the constraint.
//     * @return a Constraint object (EqualityConstraint or InequalityConstraint).
//     * @throws IllegalArgumentException if the constraint string cannot be parsed.
//     */
//    public static AbstractConstraint parse(String constraintStr) {
//        return null;
//    }
//
//    /**
//     * Constructs a constraint with given left-hand side and right-hand side expressions.
//     *
//     * @param lhs the left-hand side expression.
//     * @param rhs the right-hand side expression.
//     */
//    public AbstractConstraint(MXWrapper lhs, MXWrapper rhs) {
//        this.lhs = lhs;
//        this.rhs = rhs;
//    }
//
//    /**
//     * Returns the constraint expression in a form suitable for the solver.
//     * For equality constraints, this is typically (lhs - rhs) == 0.
//     * For inequality constraints, the expression is usually (lhs - rhs) <= 0.
//     *
//     * @return an Expression representing the constraint.
//     */
//    public abstract MXWrapper getConstraintExpression();
//
//    /**
//     * Evaluates the constraint expression using the provided variable assignment.
//     *
//     * @param env a map of variable names to their numerical values.
//     * @return the evaluated result of the constraint expression.
//     */
//    public DMVector evaluate(DMVector env) {
//        MXWrapper constraintExpression = getConstraintExpression();
//        MXVector variables = constraintExpression.symvar();
//        FunctionWrapper function = new FunctionWrapper("", variables, new MXVector(constraintExpression));
//        return function.call(env);
//    }
//
//    /**
//     * Checks if the constraint is feasible (i.e. satisfied within a given tolerance)
//     * under the provided variable assignment.
//     *
//     * @param env a map of variable names to their numerical values.
//     * @param tolerance the numerical tolerance for feasibility.
//     * @return true if the constraint is satisfied within the tolerance, false otherwise.
//     */
//    public abstract boolean isFeasible(DMVector env, double tolerance);
//
//    /**
//     * Scales the constraint by a given factor.
//     * The scaling is applied to both sides of the constraint.
//     *
//     * @param factor the scaling factor.
//     * @return a new Constraint instance representing the scaled constraint.
//     */
//    public abstract AbstractConstraint scale(double factor);

}
