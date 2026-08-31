package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Phase;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserAndFunctionSafetyTest extends TranspilationTestSupport {

    @Test
    void lexerParserAndComparisonFailuresHaveStructuredMetadata() {
        for (String source : List.of("[$]", "[.5]", "[1] unexpected")) {
            Phase phase = source.equals("[1] unexpected") ? Phase.PARSER : Phase.LEXER;
            assertFailure(() -> toMaxima.casadiToMaxima(source, variables()),
                Direction.CASADI_TO_MAXIMA, phase, source);
        }
        assertFailure(() -> toMaxima.casadiToMaxima("[a<b<c]", variables("a", "b", "c")),
            Direction.CASADI_TO_MAXIMA, Phase.SEMANTIC, "[a<b<c]");
        assertFailure(() -> toCasadi.maximaToCasadi("[var_a<var_b<var_c]",
            symbols("a", "b", "c")), Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC,
            "[var_a<var_b<var_c]");
    }

    @Test
    void casadiFunctionsAndVariablesAreDistinguishedByCallSyntax() {
        assertEquals("vn : [var_sin]$", toMaxima.casadiToMaxima("[sin]", variables("sin")));
        assertEquals("vn : [sin(var_x)]$", toMaxima.casadiToMaxima("[sin(x)]", variables("x")));
        assertFailure(() -> toMaxima.casadiToMaxima("[unknown(x)]", variables("x")),
            Direction.CASADI_TO_MAXIMA, Phase.SEMANTIC, "[unknown(x)]");
    }

    @Test
    void maximaFunctionNamesAreCaseSensitive() {
        assertEquals("sin(a)", toCasadi.maximaToCasadi("[sin(var_a)]", symbols("a")).toString());
        for (String source : List.of("[SIN(var_a)]", "[Sin(var_a)]", "[MOD(var_a,2)]")) {
            assertFailure(() -> toCasadi.maximaToCasadi(source, symbols("a")),
                Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, source);
        }
    }

    @Test
    void maximaCseVariablesHaveAnExplicitGrammarCategory() {
        assertEquals("a", toCasadi.maximaToCasadi("block([%1],%1:var_a,[%1])",
            symbols("a")).toString());
        assertFailure(() -> toCasadi.maximaToCasadi("block([temporary],temporary:var_a,[temporary])",
            symbols("a")), Direction.MAXIMA_TO_CASADI, Phase.PARSER, "temporary");
    }

    @Test
    void maximasModAndNaryExtremaKeepTheirDefinedMapping() {
        assertEquals("(a-(b*floor((a/b))))", toCasadi.maximaToCasadi("[mod(var_a,var_b)]",
            symbols("a", "b")).toString());
        assertEquals("fmin(fmin(a,b),c)", toCasadi.maximaToCasadi("[min(var_a,var_b,var_c)]",
            symbols("a", "b", "c")).toString());
    }
}
