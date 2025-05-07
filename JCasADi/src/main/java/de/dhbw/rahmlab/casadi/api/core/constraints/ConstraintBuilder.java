package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstraintBuilder {
    private static final Pattern PATTERN =
            Pattern.compile("\\s*(.+?)\\s*(<=|>=|=|<|>)\\s*(.+)\\s*");

    private MXWrapper lhs;
    private MXWrapper rhs;
    private Comparison cmp;

    private ConstraintBuilder() { }

    /** Startet einen manuellen Build-Prozess mit einer bestehenden MX-Expression. */
    public static ConstraintBuilder of(MXWrapper lhs) {
        ConstraintBuilder b = new ConstraintBuilder();
        b.lhs = lhs;
        return b;
    }

    /**
     * Parst einen String wie "x^2 + 2*x*y <= y^2 + 1" und füllt
     * lhs, rhs und cmp direkt.
     */
    public static ConstraintBuilder parse(String str) {
        Matcher m = PATTERN.matcher(str);
        if (!m.matches()) {
            throw new IllegalArgumentException("Ungültiges Constraint-Format: " + str);
        }
        String left  = m.group(1);
        String opSym = m.group(2);
        String right = m.group(3);

        // *Hier* nutzen wir unseren neuen ExpressionParser:
        MXWrapper lhsExpr = ExpressionParser.parse(left);
        MXWrapper rhsExpr = ExpressionParser.parse(right);

        // Vergleichsoperator auswählen
        Comparison cmp = Arrays.stream(Comparison.values())
                .filter(c -> c.symbol().equals(opSym))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Unbekannter Operator: " + opSym));

        return new ConstraintBuilder()
                .lhs(lhsExpr)
                .cmp(cmp)
                .rhs(rhsExpr);
    }

    public ConstraintBuilder lhs(MXWrapper lhs) {
        this.lhs = lhs;
        return this;
    }

    public ConstraintBuilder rhs(MXWrapper rhs) {
        this.rhs = rhs;
        return this;
    }

    public ConstraintBuilder cmp(Comparison cmp) {
        this.cmp = cmp;
        return this;
    }

    /**
     * Baut das finale Constraint-Objekt.
     * Wir nutzen hier den ScalarConstraint, der das Enum intern auswertet.
     */
    public Constraint build() {
        if (lhs == null || rhs == null || cmp == null) {
            throw new IllegalStateException("lhs, rhs und cmp müssen gesetzt sein");
        }
        return new ScalarConstraint(lhs, cmp, rhs);
    }
}
