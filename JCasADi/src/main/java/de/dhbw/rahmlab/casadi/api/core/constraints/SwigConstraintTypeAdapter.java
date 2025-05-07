package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.impl.casadi.ConstraintType;

/**
 * Übersetzt unsere API‑Vergleichsoperatoren und -klassen
 * auf die von SWIG erzeugte ConstraintType‑Enum.
 */
public final class SwigConstraintTypeAdapter {

    private SwigConstraintTypeAdapter() {}

    /**
     * Liefert zum gegebenen Constraint-Objekt den SWIG-ConstraintType,
     * der beim CasADi-Solver erwartet wird.
     */
    public static ConstraintType toSwigType(Constraint c) {
        Comparison cmp = c.getComparison();
        // Falls du künftig weitere Subtypen hast,
        // hier entsprechend erweitern.
        switch (cmp) {
            case EQ:
                return ConstraintType.OPTI_GENERIC_EQUALITY;
            case LE:
            case LT:
                return ConstraintType.OPTI_GENERIC_INEQUALITY;
            case GE:
            case GT:
                return ConstraintType.OPTI_GENERIC_INEQUALITY;
            default:
                throw new IllegalArgumentException("Unknown comparison: " + cmp);
        }
    }

    /**
     * Alternativ: direkt aus Comparison.
     */
    public static ConstraintType fromComparison(Comparison cmp) {
        switch (cmp) {
            case EQ:
                return ConstraintType.OPTI_GENERIC_EQUALITY;
            case LE:
                return ConstraintType.OPTI_GENERIC_INEQUALITY;
            case LT:
                return ConstraintType.OPTI_GENERIC_INEQUALITY;
            case GE:
                return ConstraintType.OPTI_GENERIC_INEQUALITY;
            case GT:
                return ConstraintType.OPTI_GENERIC_INEQUALITY;
            default:
                throw new IllegalArgumentException("Unknown comparison: " + cmp);
        }
    }
}