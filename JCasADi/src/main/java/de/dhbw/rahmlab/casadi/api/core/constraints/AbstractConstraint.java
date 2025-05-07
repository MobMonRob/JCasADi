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

    public Comparison getComparison() {
        return this.cmp;
    }

    @Override
    public String toString() {
        return lhs + " " + cmp.symbol() + " " + rhs;
    }

}
