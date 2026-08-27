package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.api.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Phase;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService;
import java.util.List;

/**
 * Manual regression harness for fail-fast ANTLR diagnostics.
 */
public class ParserFailureVerification {

    public static void main(String[] args) {
        assertCasadiConversion("[(a-2043)]", "vn : [(a - 2043)]$");
        assertCasadiConversion("[(a-136999)]", "vn : [(a - 136999)]$");
        assertCasadiConversion("[(a-9.0072e+15)]", "vn : [(a - 9.0072e+15)]$");
        assertCasadiConversion("[(a-1.80144e+16)]", "vn : [(a - 1.80144e+16)]$");
        assertCasadiConversion("[-2043]", "vn : [-(2043)]$");
        assertCasadiConversion("[(a-1e-3)]", "vn : [(a - 1e-3)]$");
        assertCasadiParserFailure("[(a+)]");
        assertMaximaParserFailure("[1,]");
        System.out.println("Lexer and fail-fast parser verification passed.");
    }

    private static void assertCasadiConversion(String source, String expected) {
        String actual = new ToMaximaTranspilerService().casadiToMaxima(source);
        if (!expected.equals(actual)) {
            throw new AssertionError("Unexpected conversion for " + source
                    + ": expected " + expected + ", got " + actual);
        }
    }

    private static void assertCasadiParserFailure(String source) {
        try {
            new ToMaximaTranspilerService().casadiToMaxima(source);
            throw new AssertionError("Expected a TranspilationException for: " + source);
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.CASADI_TO_MAXIMA, Phase.PARSER, source);
            exception.printStackTrace(System.out);
        }
    }

    private static void assertMaximaParserFailure(String source) {
        try {
            new ToCasadiTranspilerService().maximaToCasadi(source, List.of());
            throw new AssertionError("Expected a TranspilationException for: " + source);
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.MAXIMA_TO_CASADI, Phase.PARSER, source);
            exception.printStackTrace(System.out);
        }
    }

    private static void assertMetadata(TranspilationException exception,
            Direction direction, Phase phase, String source) {
        if (exception.getDirection() != direction || exception.getPhase() != phase
                || !exception.getSourceContext().contains(source)) {
            throw new AssertionError("Unexpected exception metadata: " + exception.getMessage(), exception);
        }
    }
}
