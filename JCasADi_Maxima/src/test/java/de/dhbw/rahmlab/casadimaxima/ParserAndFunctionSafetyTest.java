package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Phase;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserAndFunctionSafetyTest extends TranspilationTestSupport {

    @Test
    void lexerParserAndComparisonFailuresHaveStructuredMetadata() {
        for (String source : List.of("[$]", "[.5]", "[(arg0_0+)]", "[1] unexpected")) {
            Phase phase = source.equals("[(arg0_0+)]") ? Phase.PARSER : Phase.LEXER;
            if (source.equals("[1] unexpected")) {
                phase = Phase.PARSER;
            }
            assertFailure(() -> toMaxima.casadiToMaxima(source), Direction.CASADI_TO_MAXIMA, phase, source);
        }
        for (String source : List.of("[$]", "[.5]", "[1,]", "[1] unexpected")) {
            Phase phase = source.equals("[1,]") ? Phase.PARSER : Phase.LEXER;
            if (source.equals("[1] unexpected")) {
                phase = Phase.PARSER;
            }
            assertFailure(() -> toCasadi.maximaToCasadi(source, List.of()), Direction.MAXIMA_TO_CASADI, phase, source);
        }
        for (String source : List.of("[arg0_0<arg1_0<arg2_0]", "[arg0_0==arg1_0==arg2_0]",
                "[(arg0_0<arg1_0)==(arg2_0<arg3_0)]")) {
            assertFailure(() -> toMaxima.casadiToMaxima(source), Direction.CASADI_TO_MAXIMA,
                    Phase.SEMANTIC, source);
        }
        for (String source : List.of("[arg0_0<arg1_0<arg2_0]", "[arg0_0=arg1_0=arg2_0]",
                "[(arg0_0<arg1_0)=(arg2_0<arg3_0)]")) {
            assertFailure(() -> toCasadi.maximaToCasadi(source,
                    symbols("arg0_0", "arg1_0", "arg2_0", "arg3_0")),
                    Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, source);
        }
    }

    @Test
    void maximaFunctionNamesAreCaseSensitive() {
        assertEquals("sin(arg0_0)", toCasadi.maximaToCasadi("[sin(arg0_0)]", symbols("arg0_0")).toString());
        for (String source : List.of("[SIN(arg0_0)]", "[Sin(arg0_0)]", "[MOD(arg0_0,2)]", "[Max(arg0_0,arg1_0)]")) {
            assertFailure(() -> toCasadi.maximaToCasadi(source, symbols("arg0_0", "arg1_0")),
                    Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, source);
        }
    }

    @Test
    void maximaCseVariablesHaveAnExplicitGrammarCategory() {
        assertEquals("arg0_0", toCasadi.maximaToCasadi(
                "block([%1],%1:arg0_0,[%1])", symbols("arg0_0")).toString());

        for (String source : List.of("block([temporary],temporary:arg0_0,[temporary])",
                "block([%temporary],%temporary:arg0_0,[%temporary])", "[%1(arg0_0)]")) {
            assertFailure(() -> toCasadi.maximaToCasadi(source, symbols("arg0_0")),
                    Direction.MAXIMA_TO_CASADI, Phase.PARSER, source);
        }
    }

    @Test
    void maximasModAndNaryExtremaKeepTheirDefinedMapping() {
        assertEquals("(arg0_0-(arg1_0*floor((arg0_0/arg1_0))))",
                toCasadi.maximaToCasadi("[mod(arg0_0,arg1_0)]", symbols("arg0_0", "arg1_0")).toString());
        assertEquals("fmin(fmin(arg0_0,arg1_0),arg2_0)",
                toCasadi.maximaToCasadi("[min(arg0_0,arg1_0,arg2_0)]", symbols("arg0_0", "arg1_0", "arg2_0")).toString());
        assertEquals("fmax(fmax(arg0_0,arg1_0),arg2_0)",
                toCasadi.maximaToCasadi("[max(arg0_0,arg1_0,arg2_0)]", symbols("arg0_0", "arg1_0", "arg2_0")).toString());
    }
}
