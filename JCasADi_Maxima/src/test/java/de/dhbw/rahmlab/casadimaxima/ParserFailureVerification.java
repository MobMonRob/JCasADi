package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.api.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Phase;
import de.dhbw.rahmlab.casadimaxima.api.MaximaSimplifier;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.CasadiLexer;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.CasadiParser;
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
        assertCasadiVariableSafety();
        assertCasadiTemporaryTransport();
        assertGeneratedCasadiTemporaryTransport();
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
        assertMaximaLocalBindingSafety();
        assertCasadiParserPrecedence();
        assertMaximaLogicalPrecedence();
        assertComparisonChainSafety();
        assertLexerFailures();
        System.out.println("Lexer, parser, semantic function, and binding verification passed.");
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
        assertCasadiSemanticFailure(source, null);
    }

    private static void assertCasadiSemanticFailure(String source, String offendingToken) {
        try {
            new ToMaximaTranspilerService().casadiToMaxima(source);
            throw new AssertionError("Expected a TranspilationException for: " + source);
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.CASADI_TO_MAXIMA, Phase.SEMANTIC, source);
            assertOffendingToken(exception, offendingToken);
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
        assertMaximaSemanticFailure(source, null);
    }

    private static void assertMaximaSemanticFailure(String source, String offendingToken) {
        assertMaximaSemanticFailure(source, offendingToken, List.of());
    }

    private static void assertMaximaSemanticFailure(String source, String offendingToken,
            List<de.dhbw.rahmlab.casadi.impl.casadi.SX> inputs) {
        try {
            new ToCasadiTranspilerService().maximaToCasadi(source, inputs);
            throw new AssertionError("Expected a TranspilationException for: " + source);
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, source);
            assertOffendingToken(exception, offendingToken);
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

    private static void assertCasadiVariableSafety() {
        for (String name : List.of("v", "v1", "velocity", "vn", "_", "__")) {
            assertCasadiSemanticFailure("[" + name + "]", name);
        }
        for (String name : List.of("integrate", "next", "from", "diff", "in", "at", "limit",
                "sum", "for", "and", "elseif", "then", "else", "do", "or", "if", "unless",
                "product", "while", "thru", "step", "block", "not")) {
            assertCasadiSemanticFailure("[" + name + "]", name);
        }
        for (String name : List.of("V", "V1", "Velocity", "If", "Block", "sin", "arg0_0")) {
            assertCasadiConversion("[" + name + "]", "vn : [" + name + "]$");
        }
    }

    private static void assertCasadiTemporaryTransport() {
        String source = "@1=1, @2=(@1+2), [@2]";
        String maxima = new ToMaximaTranspilerService().casadiToMaxima(source);
        if (!maxima.contains("v1 : 1$") || !maxima.contains("v2 : (v1 + 2)$")
                || !maxima.endsWith("vn : [v2]$")) {
            throw new AssertionError("Expected mechanical @N to vN transport, got: " + maxima);
        }
    }

    private static void assertGeneratedCasadiTemporaryTransport() {
        var a = de.dhbw.rahmlab.casadi.SxStatic.sym("a");
        var b = de.dhbw.rahmlab.casadi.SxStatic.sym("b");
        var first = de.dhbw.rahmlab.casadi.SxStatic.plus(a, b);
        var second = de.dhbw.rahmlab.casadi.SxStatic.times(first, first);
        var third = de.dhbw.rahmlab.casadi.SxStatic.plus(second, first);
        var scalarExpression = de.dhbw.rahmlab.casadi.SxStatic.plus(
                de.dhbw.rahmlab.casadi.SxStatic.times(third, third),
                de.dhbw.rahmlab.casadi.SxStatic.times(third, third));
        var expression = de.dhbw.rahmlab.casadi.SxStatic.vertcat(
                new de.dhbw.rahmlab.casadi.impl.std.StdVectorSX(
                        new de.dhbw.rahmlab.casadi.impl.casadi.SX[]{scalarExpression,
                            scalarExpression}));
        String casadiText = expression.toString();
        if (casadiText.chars().filter(character -> character == '@').count() < 2) {
            throw new AssertionError("Expected CasADi to emit multiple temporary definitions, got: "
                    + casadiText);
        }
        if (new ToMaximaTranspilerService().casadiToMaxima(expression).isEmpty()) {
            throw new AssertionError("Expected generated CasADi temporary expression to transpile");
        }
    }

    private static void assertMaximaLocalBindingSafety() {
        var a = de.dhbw.rahmlab.casadi.SxStatic.sym("a");
        var b = de.dhbw.rahmlab.casadi.SxStatic.sym("b");
        List<de.dhbw.rahmlab.casadi.impl.casadi.SX> inputs = List.of(a, b);

        assertMaximaExpression("block([%1,%2],%1:a+b,%2:%1*%1,[%2])", inputs,
                de.dhbw.rahmlab.casadi.SxStatic.times(
                        de.dhbw.rahmlab.casadi.SxStatic.plus(a, b),
                        de.dhbw.rahmlab.casadi.SxStatic.plus(a, b)));
        String cseResult = new ToCasadiTranspilerService().maximaToCasadi(
                "block([%1],%1:a+b,[%1])", inputs).toString();
        if (cseResult.contains("%1")) {
            throw new AssertionError("Local CSE name leaked into CasADi expression: " + cseResult);
        }

        assertMaximaSemanticFailure("block([%1],%1:%1,[%1])", "%1");
        assertMaximaSemanticFailure("block([%1,%2],%1:%2,%2:a,[%1])", "%2");
        assertMaximaSemanticFailure("block([%1],%1:missing,[%1])", "missing");
        assertMaximaSemanticFailure("block([%1],%1:a,[missing])", "missing", inputs);
        assertMaximaSemanticFailure("block([%1],%2:a,[%2])", "%2", inputs);

        assertMaximaExpression("block([a],a:2,[a])", inputs,
                new de.dhbw.rahmlab.casadi.impl.casadi.SX(2));
        assertMaximaExpression("block([%1],%1:a+1,%1:%1+1,[%1])", inputs,
                de.dhbw.rahmlab.casadi.SxStatic.plus(
                        de.dhbw.rahmlab.casadi.SxStatic.plus(a,
                                new de.dhbw.rahmlab.casadi.impl.casadi.SX(1)),
                        new de.dhbw.rahmlab.casadi.impl.casadi.SX(1)));
        assertMaximaExpression("block([%1,%2],%1:a,[%1])", inputs, a);

        ToCasadiTranspilerService service = new ToCasadiTranspilerService();
        try {
            service.maximaToCasadi("block([%1],%1:%1,[%1])", inputs);
            throw new AssertionError("Expected local self-reference to fail");
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC,
                    "block([%1],%1:%1,[%1])");
        }
        String afterFailure = service.maximaToCasadi("[a]", inputs).toString();
        if (!"a".equals(afterFailure)) {
            throw new AssertionError("Local state leaked into next transpilation: " + afterFailure);
        }
    }

    private static void assertMaximaExpression(String source,
            List<de.dhbw.rahmlab.casadi.impl.casadi.SX> inputs,
            de.dhbw.rahmlab.casadi.impl.casadi.SX expected) {
        String actual = new ToCasadiTranspilerService().maximaToCasadi(source, inputs).toString();
        if (!expected.toString().equals(actual)) {
            throw new AssertionError("Unexpected binding conversion for " + source
                    + ": expected " + expected + ", got " + actual);
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

        var powerLexer = new MaximaLexer(CharStreams.fromString("[-2^3^4]"));
        var powerParser = new MaximaParser(new CommonTokenStream(powerLexer));
        var powerExpression = ((MaximaParser.SimpleArrayContext) powerParser.root().content())
                .arrayExpr().expression(0);
        if (!(powerExpression instanceof MaximaParser.UnaryMinusContext unaryMinus)
                || !(unaryMinus.expression() instanceof MaximaParser.PowerExprContext outerPower)
                || !(outerPower.expression(1) instanceof MaximaParser.PowerExprContext)) {
            throw new AssertionError("Expected unary minus around a right-associative power, got: "
                    + powerExpression.toStringTree(powerParser));
        }

        var precedenceLexer = new MaximaLexer(
                CharStreams.fromString("[not 1=1 and 1=0 or 1=1]"));
        var precedenceParser = new MaximaParser(new CommonTokenStream(precedenceLexer));
        var precedenceExpression = ((MaximaParser.SimpleArrayContext) precedenceParser.root().content())
                .arrayExpr().expression(0);
        if (!(precedenceExpression instanceof MaximaParser.LogicalOrExprContext orExpression)
                || !(orExpression.expression(0) instanceof MaximaParser.LogicalAndExprContext andExpression)
                || !(andExpression.expression(0) instanceof MaximaParser.NotExprContext notExpression)
                || !(notExpression.expression() instanceof MaximaParser.CompareExprContext)) {
            throw new AssertionError("Expected comparison before not, and, and or, got: "
                    + precedenceExpression.toStringTree(precedenceParser));
        }
    }

    private static void assertCasadiParserPrecedence() {
        var relationLexer = new CasadiLexer(CharStreams.fromString("[a<b==c]"));
        var relationParser = new CasadiParser(new CommonTokenStream(relationLexer));
        var relationExpression = relationParser.file().array().expr(0);
        if (!(relationExpression instanceof CasadiParser.EqualityOpsContext equality)
                || !(equality.expr(0) instanceof CasadiParser.RelationalOpsContext)) {
            throw new AssertionError("Expected equality around a stronger relational expression, got: "
                    + relationExpression.toStringTree(relationParser));
        }

        var ternaryLexer = new CasadiLexer(CharStreams.fromString("[a?b:c?d:e]"));
        var ternaryParser = new CasadiParser(new CommonTokenStream(ternaryLexer));
        var ternaryExpression = ternaryParser.file().array().expr(0);
        if (!(ternaryExpression instanceof CasadiParser.TernaryOpContext ternary)
                || !(ternary.expr(2) instanceof CasadiParser.TernaryOpContext)) {
            throw new AssertionError("Expected a right-associative ternary expression, got: "
                    + ternaryExpression.toStringTree(ternaryParser));
        }
    }

    private static void assertComparisonChainSafety() {
        for (String source : List.of("[a<b<c]", "[a==b==c]", "[(a<b)==(c<d)]")) {
            assertCasadiSemanticFailure(source);
        }
        assertCasadiConversion("[!(a==b)]", "vn : [not ((a = b))]$");
        assertCasadiConversion("[a<b?c:d]", "vn : [if (a < b) then c else d]$");

        List<de.dhbw.rahmlab.casadi.impl.casadi.SX> inputs = List.of(
                de.dhbw.rahmlab.casadi.SxStatic.sym("a"),
                de.dhbw.rahmlab.casadi.SxStatic.sym("b"),
                de.dhbw.rahmlab.casadi.SxStatic.sym("c"),
                de.dhbw.rahmlab.casadi.SxStatic.sym("d"));
        for (String source : List.of("[a<b<c]", "[a=b=c]", "[(a<b)=(c<d)]")) {
            assertMaximaSemanticFailure(source, null, inputs);
        }
        assertMaximaConversion("[not (a=b) and (c<d)]", inputs);
    }

    private static void assertLexerFailures() {
        assertCasadiLexerFailure("[$]", "$", 1, 1);
        assertCasadiLexerFailure("[.5]", ".", 1, 1);
        assertMaximaLexerFailure("[$]", "$", 1, 1);
        assertMaximaLexerFailure("[.5]", ".", 1, 1);
    }

    private static void assertCasadiLexerFailure(String source, String offendingToken,
            int line, int column) {
        try {
            new ToMaximaTranspilerService().casadiToMaxima(source);
            throw new AssertionError("Expected a lexer TranspilationException for: " + source);
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.CASADI_TO_MAXIMA, Phase.LEXER, source);
            assertLexerPosition(exception, offendingToken, line, column);
        }
    }

    private static void assertMaximaLexerFailure(String source, String offendingToken,
            int line, int column) {
        try {
            new ToCasadiTranspilerService().maximaToCasadi(source, List.of());
            throw new AssertionError("Expected a lexer TranspilationException for: " + source);
        } catch (TranspilationException exception) {
            assertMetadata(exception, Direction.MAXIMA_TO_CASADI, Phase.LEXER, source);
            assertLexerPosition(exception, offendingToken, line, column);
        }
    }

    private static void assertMaximaConversion(String source,
            List<de.dhbw.rahmlab.casadi.impl.casadi.SX> inputs) {
        if (new ToCasadiTranspilerService().maximaToCasadi(source, inputs) == null) {
            throw new AssertionError("Expected a converted SX value for: " + source);
        }
    }

    private static void assertMetadata(TranspilationException exception,
            Direction direction, Phase phase, String source) {
        if (exception.getDirection() != direction || exception.getPhase() != phase
                || !exception.getSourceContext().contains(source)) {
            throw new AssertionError("Unexpected exception metadata: " + exception.getMessage(), exception);
        }
    }

    private static void assertOffendingToken(TranspilationException exception, String expected) {
        if (expected != null && !expected.equals(exception.getOffendingToken())) {
            throw new AssertionError("Unexpected offending token: expected " + expected
                    + ", got " + exception.getOffendingToken(), exception);
        }
    }

    private static void assertLexerPosition(TranspilationException exception, String offendingToken,
            int line, int column) {
        assertOffendingToken(exception, offendingToken);
        if (exception.getLine() != line || exception.getColumn() != column) {
            throw new AssertionError("Unexpected lexer position: expected " + line + ":" + column
                    + ", got " + exception.getLine() + ":" + exception.getColumn(), exception);
        }
    }
}
