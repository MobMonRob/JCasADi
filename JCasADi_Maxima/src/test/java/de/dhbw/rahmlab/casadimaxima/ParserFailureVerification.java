package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.api.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Phase;
import de.dhbw.rahmlab.casadimaxima.api.MaximaSimplifier;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.MaximaLexer;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.MaximaParser;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService;
import java.util.List;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

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
        assertCasadiConversion("[a<b&&c<d]", "vn : [((a < b) and (c < d))]$");
        assertCasadiConversion("[a||b&&c]", "vn : [(a or (b and c))]$");
        assertCasadiConversion("[sin(a)]", "vn : [sin(a)]$");
        assertCasadiConversion("[atan2(a,b)]", "vn : [atan2(a, b)]$");
        assertCasadiConversion("[fmax(a,b+1)]", "vn : [max(a, b + 1)]$");
        assertMaximaConversion("[sin(1)]");
        assertMaximaConversion("[max(1,2)]");
        assertCasadiParserFailure("[(a+)]");
        assertMaximaParserFailure("[1,]");
        assertMaximaParserFailure("[1] unexpected");
        assertCasadiSemanticFailure("[sin()]");
        assertCasadiSemanticFailure("[sin(a,b)]");
        assertCasadiConversion("[fmod(a,b)]", "vn : [signum(a) * mod(abs(a), abs(b))]$");
        assertCasadiConversion("[copysign(a,b)]", "vn : [abs(a) * (signum(b) + 1 - signum(b)^2)]$");
        assertCasadiSemanticFailure("[remainder(a,b)]");
        assertCasadiSemanticFailure("[erfinv(a)]");
        assertCasadiSemanticFailure("[unknown(a)]");
        assertCasadiSemanticFailure("[fmod(a)]");
        assertCasadiSemanticFailure("[fmod(a,0)]");
        assertCasadiSemanticFailure("[copysign(a,b,c)]");
        assertMaximaSemanticFailure("[sin()]");
        assertMaximaSemanticFailure("[sin(1,2)]");
        assertMaximaSemanticFailure("[mod(1,0)]");
        assertMaximaSemanticFailure("[mod(1,2,3)]");
        assertMaximaSemanticFailure("[lmin(1,2)]");
        assertMaximaSemanticFailure("[unknown(1)]");
        assertMaximaSemanticFailure("[min()]");
        assertMaximaSemanticFailure("[max()]");
        assertMaximaFunctionFold("[min(a,b,c)]", "fmin(fmin(a,b),c)");
        assertMaximaFunctionFold("[max(a,b,c)]", "fmax(fmax(a,b),c)");
        assertMaximaFunctionFold("[min(min(a,b),c)]", "fmin(fmin(a,b),c)");
        assertMaximaFunctionFold("[max(max(a,b),c)]", "fmax(fmax(a,b),c)");
        assertMaximaFunctionFold("[min(a)]", "a");
        assertMaximaFunctionFold("[max(a)]", "a");
        assertMaximaArithmetic("[mod(5,3)]", "2");
        assertMaximaArithmetic("[mod(5,-3)]", "-1");
        assertMaximaArithmetic("[mod(-5,3)]", "1");
        assertMaximaArithmetic("[mod(-5,-3)]", "-2");
        assertMaximaRoundTrip("[fmod(5,3)]", "2");
        assertMaximaRoundTrip("[fmod(5,-3)]", "2");
        assertMaximaRoundTrip("[fmod(-5,3)]", "-2");
        assertMaximaRoundTrip("[fmod(-5,-3)]", "-2");
        assertMaximaRoundTrip("[copysign(-2,-3)]", "-2");
        assertMaximaRoundTrip("[copysign(-2,0)]", "2");
        assertMaximaRoundTrip("[copysign(-2,3)]", "2");
        assertSymbolicCopysignRoundTrip();
        assertMaximaLogicalPrecedence();
        System.out.println("Lexer, parser, and semantic function verification passed.");
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

    private static void assertCasadiSemanticFailure(String source) {
        try {
            new ToMaximaTranspilerService().casadiToMaxima(source);
            throw new AssertionError("Expected a TranspilationException for: " + source);
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.CASADI_TO_MAXIMA, Phase.SEMANTIC, source);
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

    private static void assertMaximaSemanticFailure(String source) {
        try {
            new ToCasadiTranspilerService().maximaToCasadi(source, List.of());
            throw new AssertionError("Expected a TranspilationException for: " + source);
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, source);
        }
    }

    private static void assertMaximaConversion(String source) {
        if (new ToCasadiTranspilerService().maximaToCasadi(source, List.of()) == null) {
            throw new AssertionError("Expected a converted SX value for: " + source);
        }
    }

    private static void assertMaximaFunctionFold(String source, String expected) {
        String actual = new ToCasadiTranspilerService().maximaToCasadi(source,
                List.of(de.dhbw.rahmlab.casadi.SxStatic.sym("a"),
                        de.dhbw.rahmlab.casadi.SxStatic.sym("b"),
                        de.dhbw.rahmlab.casadi.SxStatic.sym("c"))).toString();
        if (!expected.equals(actual)) {
            throw new AssertionError("Unexpected fold for " + source
                    + ": expected " + expected + ", got " + actual);
        }
    }

    private static void assertMaximaArithmetic(String source, String expected) {
        String actual = new ToCasadiTranspilerService().maximaToCasadi(source, List.of()).toString();
        if (!expected.equals(actual)) {
            throw new AssertionError("Unexpected arithmetic conversion for " + source
                    + ": expected " + expected + ", got " + actual);
        }
    }

    private static void assertMaximaRoundTrip(String casadiSource, String expected) {
        String maximaInput = new ToMaximaTranspilerService().casadiToMaxima(casadiSource);
        if (maximaInput.contains("fmod(") || maximaInput.contains("copysign(")) {
            throw new AssertionError("Opaque function marker in Maxima input: " + maximaInput);
        }
        String maximaOutput = MaximaSimplifier.simplify(maximaInput);
        String actual = new ToCasadiTranspilerService().maximaToCasadi(maximaOutput, List.of()).toString();
        if (!expected.equals(actual)) {
            throw new AssertionError("Unexpected round trip for " + casadiSource
                    + ": expected " + expected + ", got " + actual
                    + " (Maxima output: " + maximaOutput + ")");
        }
    }

    private static void assertSymbolicCopysignRoundTrip() {
        String maximaInput = new ToMaximaTranspilerService()
                .casadiToMaxima("[copysign(a,b),copysign(a,b)]");
        String maximaOutput = MaximaSimplifier.simplify(maximaInput);
        if (!maximaOutput.startsWith("block(")) {
            throw new AssertionError("Expected Maxima optimize to extract common subexpressions: "
                    + maximaOutput);
        }
        if (new ToCasadiTranspilerService().maximaToCasadi(maximaOutput,
                List.of(de.dhbw.rahmlab.casadi.SxStatic.sym("a"),
                        de.dhbw.rahmlab.casadi.SxStatic.sym("b"))) == null) {
            throw new AssertionError("Expected symbolic copysign round trip to produce SX");
        }
    }

    private static void assertMaximaLogicalPrecedence() {
        var lexer = new MaximaLexer(CharStreams.fromString("[(1=1) or (1=0) and (1=0)]"));
        var parser = new MaximaParser(new CommonTokenStream(lexer));
        var root = parser.root();
        var expression = ((MaximaParser.SimpleArrayContext) root.content())
                .arrayExpr().expression(0);
        if (!(expression instanceof MaximaParser.LogicalOrExprContext orContext)
                || !(orContext.expression(1) instanceof MaximaParser.LogicalAndExprContext)) {
            throw new AssertionError("Expected OR with an AND right operand, got: "
                    + root.toStringTree(parser));
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
