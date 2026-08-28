package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;
import de.dhbw.rahmlab.casadimaxima.api.MaximaProcessor;
import de.dhbw.rahmlab.casadimaxima.implementation.maxima.MaximaSimplifier;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Phase;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class InputSymbolContractTest extends TranspilationTestSupport {

    @Test
    void publicPipelineAndSpiProduceTheSameResult() {
        SX expression = SxStatic.sym("arg0", 2, 1);
        List<SX> variables = List.of(expression);
        String expected = MaximaSimplifier.simplify_pipeline(expression, variables).toString();

        assertEquals(expected, new MaximaProcessor().simplifySparsify(expression, variables).toString());
    }

    @Test
    void argumentComponentsPassThroughWithoutTransportNames() {
        String maxima = toMaxima.casadiToMaxima("@1=arg0_0, [@1+arg1_2]");
        assertEquals("v1 : arg0_0$\nvn : [(v1 + arg1_2)]$", maxima);
        assertFalse(maxima.contains("vjcx"));
        assertEquals("(arg0_0+arg1_2)", toCasadi.maximaToCasadi("[arg0_0+arg1_2]",
            symbols("arg0_0", "arg1_2")).toString());
    }

    @Test
    void freeCasadiIdentifiersAreRejectedInEveryStringEntryPoint() {
        for (String source : List.of("[a]", "[simp]", "[display2d]", "[sin(a)]")) {
            assertFailure(() -> toMaxima.casadiToMaxima(source), Direction.CASADI_TO_MAXIMA,
                Phase.PARSER, source);
        }
    }

    @Test
    void inputVariableApiRequiresCasadiArgumentComponentNames() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> toCasadi.maximaToCasadi("[a]", symbols("a")));
        assertTrue(exception.getMessage().contains("argN_M"));
    }

    @Test
    void unknownFreeMaximaVariablesStillFailClosedAndCseLocalsRemainLocal() {
        assertFailure(() -> toCasadi.maximaToCasadi("[arg9_9]", symbols("arg0_0")),
            Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, "[arg9_9]");
        assertFailure(() -> toCasadi.maximaToCasadi("[foreign]", List.of()),
            Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, "[foreign]");
        assertEquals("(arg0_0+1)", toCasadi.maximaToCasadi(
            "block([%1],%1:arg0_0+1,[%1])", symbols("arg0_0")).toString());
    }

    @Test
    void latexUsesOriginalArgumentComponentNames() {
        SX input = SxStatic.sym("arg0", 2, 1);
        String latex = new MaximaProcessor().LaTeXify(SxStatic.vertcat(
            new StdVectorSX(new SX[]{input, new SX(1)})));
        assertTrue(latex.contains("arg"));
        assertTrue(latex.contains("0,0"));
        assertFalse(latex.contains("vjcx"));
    }

    @Test
    void maximaDoesNotConsumeArgumentComponentNames() {
        String maxima = toMaxima.casadiToMaxima("[arg0_0]");
        assertEquals("[arg0_0]", MaximaSimplifier.simplify_internal(maxima));
    }
}
