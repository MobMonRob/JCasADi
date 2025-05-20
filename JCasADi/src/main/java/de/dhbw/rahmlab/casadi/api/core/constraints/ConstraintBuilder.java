package de.dhbw.rahmlab.casadi.api.core.constraints;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Builder class for constructing Constraint objects.
 * Provides methods to parse and manually build constraints from expressions.
 */
public class ConstraintBuilder {

    /** Regular expression pattern to parse constraint strings. */
    private static final Pattern PATTERN =
            Pattern.compile("\\s*(.+?)\\s*(<=|>=|=|<|>)\\s*(.+)\\s*");

    /** The left-hand side expression of the constraint. */
    private MXWrapper lhs;

    /** The right-hand side expression of the constraint. */
    private MXWrapper rhs;

    /** The comparison operator used in the constraint. */
    private Comparison cmp;

    /** Private constructor to prevent direct instantiation. */
    private ConstraintBuilder() { }

    /**
     * Starts a manual build process with an existing MX expression.
     *
     * @param lhs the left-hand side expression
     * @return a new instance of ConstraintBuilder initialized with the given lhs
     */
    public static ConstraintBuilder of(MXWrapper lhs) {
        ConstraintBuilder b = new ConstraintBuilder();
        b.lhs = lhs;
        return b;
    }

    /**
     * Parses a string representation of a constraint, such as "x^2 + 2*x*y <= y^2 + 1",
     * and fills the lhs, rhs, and cmp fields accordingly.
     *
     * @param str the string representation of the constraint
     * @return a new instance of ConstraintBuilder with parsed values
     * @throws IllegalArgumentException if the string format is invalid
     */
    public static ConstraintBuilder parse(String str) {
        Matcher m = PATTERN.matcher(str);
        if (!m.matches()) {
            throw new IllegalArgumentException("Ungültiges Constraint-Format: " + str);
        }
        String left  = m.group(1);
        String opSym = m.group(2);
        String right = m.group(3);

        // Use the ExpressionParser to parse the expressions
        MXWrapper lhsExpr = ExpressionParser.parse(left);
        MXWrapper rhsExpr = ExpressionParser.parse(right);

        // Select the comparison operator
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

    /**
     * Sets the left-hand side expression for the constraint.
     *
     * @param lhs the left-hand side expression
     * @return the current instance of ConstraintBuilder
     */
    public ConstraintBuilder lhs(MXWrapper lhs) {
        this.lhs = lhs;
        return this;
    }

    /**
     * Sets the right-hand side expression for the constraint.
     *
     * @param rhs the right-hand side expression
     * @return the current instance of ConstraintBuilder
     */
    public ConstraintBuilder rhs(MXWrapper rhs) {
        this.rhs = rhs;
        return this;
    }

    /**
     * Sets the comparison operator for the constraint.
     *
     * @param cmp the comparison operator
     * @return the current instance of ConstraintBuilder
     */
    public ConstraintBuilder cmp(Comparison cmp) {
        this.cmp = cmp;
        return this;
    }

    /**
     * Builds the final Constraint object using the set lhs, rhs, and cmp.
     * Utilizes the ScalarConstraint which internally evaluates the comparison operator.
     *
     * @return a new instance of Constraint
     * @throws IllegalStateException if lhs, rhs, or cmp are not set
     */
    public Constraint build() {
        if (lhs == null || rhs == null || cmp == null) {
            throw new IllegalStateException("lhs, rhs und cmp müssen gesetzt sein");
        }
        return new ScalarConstraint(lhs, cmp, rhs);
    }
}
