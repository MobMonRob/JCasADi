package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.api.MaximaSimplifier;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Phase;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaximaOutputSafetyTest extends TranspilationTestSupport {

    @Test
    void groupsTransparentFunctionsAndPreservesDivision() {
        String output = toMaxima.casadiToMaxima("[arg2_0/fmod(arg0_0,arg1_0),copysign(arg0_0,arg1_0)]");
        assertEquals("vn : [(arg2_0 / (signum(arg0_0) * mod(abs(arg0_0), abs(arg1_0)))), "
                + "(abs(arg0_0) * (signum(arg1_0) + 1 - signum(arg1_0)^2))]$", output);
        assertFalse(output.contains("fmod("));
        assertFalse(output.contains("copysign("));
    }

    @Test
    void fmodRoundTripIsNumericallyAndSymbolicallyGrouped() {
        String maximaInput = toMaxima.casadiToMaxima("[12/fmod(5,3)]");
        assertEquals("[6]", MaximaSimplifier.simplify(maximaInput));

        String symbolicInput = toMaxima.casadiToMaxima("[arg2_0/fmod(arg0_0,arg1_0)]");
        String maximaOutput = MaximaSimplifier.simplify(symbolicInput);
        String casadi = toCasadi.maximaToCasadi(maximaOutput,
                symbols("arg0_0", "arg1_0", "arg2_0")).toString();
        assertFalse(casadi.contains("vjcx"));
        assertTrue(casadi.contains("arg0_0"));
        assertTrue(casadi.contains("arg1_0"));
        assertTrue(casadi.contains("arg2_0"));
    }

    @Test
    void existingFmodAndCopysignRoundTripsRemainCovered() {
        for (String[] testCase : new String[][]{
                {"[fmod(5,3)]", "2"}, {"[fmod(5,-3)]", "2"},
                {"[fmod(-5,3)]", "-2"}, {"[fmod(-5,-3)]", "-2"},
                {"[copysign(-2,-3)]", "-2"}, {"[copysign(-2,0)]", "2"},
                {"[copysign(-2,3)]", "2"}}) {
            String maxima = MaximaSimplifier.simplify(toMaxima.casadiToMaxima(testCase[0]));
            assertEquals(testCase[1], toCasadi.maximaToCasadi(maxima, List.of()).toString());
        }
    }

    @Test
    void notLogicAndTernaryAreStructurallyPreserved() {
        assertEquals("vn : [((not (arg0_0)) + arg1_0)]$", toMaxima.casadiToMaxima("[!arg0_0+arg1_0]"));
        assertEquals("vn : [((not (arg0_0)) = arg1_0)]$", toMaxima.casadiToMaxima("[!arg0_0==arg1_0]"));
        assertEquals("vn : [(if arg0_0 then (if arg1_0 then arg2_0 else arg3_0) else arg4_0)]$",
                toMaxima.casadiToMaxima("[arg0_0?arg1_0?arg2_0:arg3_0:arg4_0]"));
        assertEquals("vn : [((not (((arg0_0 = arg1_0)))) and ((arg2_0 < arg3_0)))]$",
                toMaxima.casadiToMaxima("[!(arg0_0==arg1_0)&&(arg2_0<arg3_0)]"));
    }

    @Test
    void knownFunctionContractsRemainFailClosed() {
        for (String source : List.of("[fmax(1)]", "[fmod(1)]", "[copysign(1,2,3)]",
                "[remainder(1,2)]", "[erfinv(1)]", "[unknown(1)]", "[fmod(1,0)]")) {
            assertFailure(() -> toMaxima.casadiToMaxima(source), Direction.CASADI_TO_MAXIMA,
                    Phase.SEMANTIC, source);
        }
        for (String source : List.of("[mod(1,0)]", "[mod(1,2,3)]", "[lmin(1,2)]",
                "[unknown(1)]", "[min()]", "[max()]")) {
            assertFailure(() -> toCasadi.maximaToCasadi(source, List.of()), Direction.MAXIMA_TO_CASADI,
                    Phase.SEMANTIC, source);
        }
    }
}
