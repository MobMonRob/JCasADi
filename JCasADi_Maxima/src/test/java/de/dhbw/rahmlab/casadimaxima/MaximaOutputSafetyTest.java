package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.implementation.maxima.MaximaSimplifier;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Phase;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaximaOutputSafetyTest extends TranspilationTestSupport {

    @Test
    void groupsTransparentFunctionsAndUsesTransportNames() {
        String output = toMaxima.casadiToMaxima("[z/fmod(x,y),copysign(x,y)]",
            variables("x", "y", "z"));
        assertEquals("vn : [(var_z / (signum(var_x) * mod(abs(var_x), abs(var_y)))), "
            + "(abs(var_x) * (signum(var_y) + 1 - signum(var_y)^2))]$", output);
        assertFalse(output.contains("fmod("));
        assertFalse(output.contains("copysign("));
    }

    @Test
    void fmodRoundTripPreservesOriginalSymbolNames() {
        String maximaInput = toMaxima.casadiToMaxima("[z/fmod(x,y)]", variables("x", "y", "z"));
        String maximaOutput = MaximaSimplifier.simplify_internal(maximaInput);
        String casadi = toCasadi.maximaToCasadi(maximaOutput, symbols("x", "y", "z")).toString();
        assertTrue(casadi.contains("x"));
        assertTrue(casadi.contains("y"));
        assertTrue(casadi.contains("z"));
    }

    @Test
    void numericFunctionRoundTripsNeedNoVariables() {
        String maxima = MaximaSimplifier.simplify_internal(
            toMaxima.casadiToMaxima("[fmod(5,3)]", variables()));
        assertEquals("2", toCasadi.maximaToCasadi(maxima, List.of()).toString());
    }

    @Test
    void notLogicAndTernaryAreStructurallyPreserved() {
        assertEquals("vn : [((not (var_a)) + var_b)]$",
            toMaxima.casadiToMaxima("[!a+b]", variables("a", "b")));
        assertEquals("vn : [(if var_a then var_b else var_c)]$",
            toMaxima.casadiToMaxima("[a?b:c]", variables("a", "b", "c")));
    }

    @Test
    void knownFunctionContractsRemainFailClosed() {
        for (String source : List.of("[fmax(1)]", "[unknown(1)]", "[fmod(1,0)]")) {
            assertFailure(() -> toMaxima.casadiToMaxima(source, variables()),
                Direction.CASADI_TO_MAXIMA, Phase.SEMANTIC, source);
        }
        assertFailure(() -> toCasadi.maximaToCasadi("[unknown(1)]", List.of()),
            Direction.MAXIMA_TO_CASADI, Phase.SEMANTIC, "[unknown(1)]");
    }
}
