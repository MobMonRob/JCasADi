package de.dhbw.rahmlab.casadimaxima.implementation.transpilation;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

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
        PARSER,
        SEMANTIC
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

    public static TranspilationException semantic(Direction direction, String source,
        ParserRuleContext context, String message) {
        Token start = context.getStart();
        return at(direction, Phase.SEMANTIC, source, start.getLine(),
            start.getCharPositionInLine(), start.getText(), start.getStartIndex(),
            start.getText().length(), message, null);
    }

    public static TranspilationException at(Direction direction, Phase phase, String source,
        int line, int column, String offendingToken, int sourceIndex, int tokenLength,
        String message, Throwable cause) {
        return new TranspilationException(direction, phase, line, column, offendingToken,
            sourceContext(source, sourceIndex, tokenLength), message, cause);
    }

    private static String sourceContext(String source, int sourceIndex, int tokenLength) {
        final int contextRadius = 80;
        int safeIndex = Math.max(0, Math.min(sourceIndex, source.length()));
        int start = Math.max(0, safeIndex - contextRadius);
        int finish = Math.min(source.length(), safeIndex + Math.max(1, tokenLength) + contextRadius);
        String excerpt = source.substring(start, finish);
        String marker = " ".repeat(safeIndex - start) + "^";
        return String.format("Source context:%n%s%n%s", excerpt, marker);
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
