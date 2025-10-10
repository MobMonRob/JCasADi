package de.dhbw.rahmlab.casadi.api.core.constraints;

/**
 * Enum representing different types of comparison operators used in constraints.
 * Each comparison operator is associated with a symbol and a corresponding dot function.
 */
public enum Comparison {

    /** Equality comparison operator. */
    EQ("=",   "=="),

    /** Less than or equal to comparison operator. */
    LE("<=",  "<="),

    /** Greater than or equal to comparison operator. */
    GE(">=",  ">="),

    /** Less than comparison operator. */
    LT("<",   "<"),

    /** Greater than comparison operator. */
    GT(">",   ">");

    /** The symbol representing the comparison operator. */
    private final String symbol;

    /** The dot function associated with the comparison operator. */
    public final String dotFunction;

    /**
     * Constructs a Comparison enum with specified symbol and dot function.
     *
     * @param symbol the symbol representing the comparison operator
     * @param dotFunction the dot function associated with the comparison operator
     */
    Comparison(String symbol, String dotFunction) {
        this.symbol = symbol;
        this.dotFunction = dotFunction;
    }

    /**
     * Returns the symbol of the comparison operator.
     *
     * @return the symbol as a String
     */
    public String symbol() {
        return symbol;
    }

    /**
     * Returns the dot function associated with the comparison operator.
     *
     * @return the dot function as a String
     */
    public String dotFunc() {
        return dotFunction;
    }

    /**
     * Returns the current comparison operator.
     *
     * @return the current Comparison instance
     */
    public Comparison getComparison() {
        return this;
    }

}
