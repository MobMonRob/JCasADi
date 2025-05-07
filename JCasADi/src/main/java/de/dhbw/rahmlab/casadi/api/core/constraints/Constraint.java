package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dbl.MapStringToDouble;

public interface Constraint {

    /**
     * Returns the pure MX expression that represents (lhs - rhs)
     *
     * @return an instance of MXWrapper
     */
    MXWrapper getExpression();

    /**
     * True, wenn under assignment (z.B. Map<Var,Value>) |lhs–rhs| ≤ tolerance.
     */
    boolean isFeasible(MapStringToDouble env, double tolerance);

    /**
     * Gibt eine neue Constraint-Instanz zurück, bei der lhs und rhs mit factor multipliziert wurden.
     */
    Constraint scaledBy(double factor);

    Comparison getComparison();

    /**
     * String-Repräsentation wie "x^2 + 1 <= y"
     */
    @Override
    String toString();

    /**
     * Parsen eines Strings wie "x^2 + 1 <= y" oder "x = y + 2"
     * liefert automatisch die passende Equality- oder Inequality-Instanz.
     */
    static Constraint parse(String s) {
        return ConstraintBuilder.parse(s).build();
    }

}
