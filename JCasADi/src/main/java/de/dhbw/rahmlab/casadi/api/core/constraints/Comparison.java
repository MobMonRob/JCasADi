package de.dhbw.rahmlab.casadi.api.core.constraints;

public enum Comparison {
    EQ("=",   "=="),   // Equality
    LE("<=",  "<="),   // less than or equal to
    GE(">=",  ">="),   // greater than or equal to
    LT("<",   "<"),    // less than
    GT(">",   ">");    // greater than

    private final String symbol, dotFunction;

    Comparison(String symbol, String dotFunction) {
        this.symbol = symbol;
        this.dotFunction = dotFunction;
    }

    public String symbol() {
        return symbol;
    }

    public String dotFunc() {
        return dotFunction;
    }

}
