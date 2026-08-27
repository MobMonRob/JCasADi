package de.dhbw.rahmlab.casadimaxima.parsing;

import de.dhbw.rahmlab.casadimaxima.api.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Phase;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

/**
 * Converts every ANTLR syntax error into an exception instead of allowing
 * error recovery to continue with a partial parse tree.
 */
public class FailFastErrorListener extends BaseErrorListener {

    private static final int CONTEXT_RADIUS = 80;

    private final Direction direction;
    private final Phase phase;
    private final String source;

    public FailFastErrorListener(Direction direction, Phase phase, String source) {
        this.direction = direction;
        this.phase = phase;
        this.source = source;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
            int line, int charPositionInLine, String message, RecognitionException exception) {
        Token token = offendingSymbol instanceof Token ? (Token) offendingSymbol : null;
        int sourceIndex = token != null && token.getStartIndex() >= 0
                ? token.getStartIndex() : indexAt(line, charPositionInLine);
        String offendingToken = token != null ? token.getText() : "<unknown>";

        throw new TranspilationException(direction, phase, line, charPositionInLine,
                offendingToken, sourceContext(sourceIndex, offendingToken.length()), message, exception);
    }

    private int indexAt(int line, int column) {
        int index = 0;
        for (int currentLine = 1; currentLine < line && index < source.length(); currentLine++) {
            int newline = source.indexOf('\n', index);
            if (newline < 0) {
                return source.length();
            }
            index = newline + 1;
        }
        return Math.min(index + column, source.length());
    }

    private String sourceContext(int sourceIndex, int tokenLength) {
        int safeIndex = Math.max(0, Math.min(sourceIndex, source.length()));
        int start = Math.max(0, safeIndex - CONTEXT_RADIUS);
        int finish = Math.min(source.length(), safeIndex + Math.max(1, tokenLength) + CONTEXT_RADIUS);
        String excerpt = source.substring(start, finish);
        String marker = " ".repeat(safeIndex - start) + "^";
        return String.format("Source context:%n%s%n%s", excerpt, marker);
    }
}
