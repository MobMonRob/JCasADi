package de.dhbw.rahmlab.casadimaxima.api;

/**
 * Indicates that an expression could not be parsed during transpilation.
 */
public class TranspilationException extends RuntimeException {

    public enum Direction {
        CASADI_TO_MAXIMA,
        MAXIMA_TO_CASADI
    }

    public enum Phase {
        LEXER,
        PARSER
    }

    private final Direction direction;
    private final Phase phase;
    private final int line;
    private final int column;
    private final String offendingToken;
    private final String sourceContext;

    public TranspilationException(Direction direction, Phase phase, int line,
            int column, String offendingToken, String sourceContext,
            String message, Throwable cause) {
        super(String.format("%s %s error at line %d, column %d near \"%s\": %s%n%s",
                direction, phase, line, column, offendingToken, message, sourceContext), cause);
        this.direction = direction;
        this.phase = phase;
        this.line = line;
        this.column = column;
        this.offendingToken = offendingToken;
        this.sourceContext = sourceContext;
    }

    public Direction getDirection() {
        return direction;
    }

    public Phase getPhase() {
        return phase;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getOffendingToken() {
        return offendingToken;
    }

    public String getSourceContext() {
        return sourceContext;
    }
}
