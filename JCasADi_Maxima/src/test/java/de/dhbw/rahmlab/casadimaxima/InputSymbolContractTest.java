package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.implementation.maxima.MaximaSimplifier;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Phase;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class InputSymbolContractTest extends TranspilationTestSupport {

    @Test
    void explicitFreeSymbolsAreEncodedForMaxima() {
        String maxima = toMaxima.casadiToMaxima("@1=simp, [@1+display2d+sin]",
            variables("simp", "display2d", "sin"));
        assertEquals("v1 : var_simp$\nvn : [((v1 + var_display2d) + var_sin)]$", maxima);
    }

    @Test
    void sxEntryPointDiscoversFreeSymbolsAutomatically() {
        SX expression = SxStatic.plus(symbol("simp"), symbol("var_x"));
        assertEquals("vn : [((var_simp + var_var_x))]$", toMaxima.casadiToMaxima(expression));
    }

    @Test
    void rawStringRequiresAnExplicitVariableAllowList() {
        assertFailure(() -> toMaxima.casadiToMaxima("[a]", variables()),
            Direction.CASADI_TO_MAXIMA, Phase.SEMANTIC, "[a]");
        assertFailure(() -> toMaxima.casadiToMaxima("[unknown(a)]", variables("a")),
            Direction.CASADI_TO_MAXIMA, Phase.SEMANTIC, "[unknown(a)]");
    }

    @Test
    void maximaNamesAreDecodedOnlyThroughTheTransportMap() {
        assertEquals("(simp+var_x)", toCasadi.maximaToCasadi("[var_simp+var_var_x]",
            symbols("simp", "var_x")).toString());
        assertFailure(() -> toCasadi.maximaToCasadi("[simp]", symbols("simp")),
            Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, "[simp]");
        assertFailure(() -> toCasadi.maximaToCasadi("[var_foreign]", List.of()),
            Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, "[var_foreign]");
    }

    @Test
    void cseNamesAreNotEncoded() {
        String maxima = toMaxima.casadiToMaxima("@1=a, [@1]", variables("a"));
        assertEquals("v1 : var_a$\nvn : [v1]$", maxima);
        assertEquals("(a+1)", toCasadi.maximaToCasadi(
            "block([%1],%1:var_a+1,[%1])", symbols("a")).toString());
    }

    @Test
    void pipelineRetainsOriginalSymbolsAfterRoundTrip() {
        SX expression = SxStatic.sym("simp", 2, 1);
        List<SX> inputs = List.of(expression);
        assertEquals(MaximaSimplifier.simplify_pipeline(expression, inputs).toString(),
            new de.dhbw.rahmlab.casadimaxima.api.MaximaProcessor()
                .simplifySparsify(expression, inputs).toString());
    }

    @Test
    void duplicateTransportNamesAreRejected() {
        assertThrows(IllegalArgumentException.class,
            () -> toCasadi.maximaToCasadi("[var_a]", List.of(symbol("a"), symbol("a"))));
    }
}
