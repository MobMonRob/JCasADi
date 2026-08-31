package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.VariableNameCodec;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VariableNameCodecTest {

    @Test
    void encodingIsInjectiveAndReversible() {
        assertEquals("var_x", VariableNameCodec.encode("x"));
        assertEquals("var_var_x", VariableNameCodec.encode("var_x"));
        assertEquals("var_x", VariableNameCodec.decode("var_var_x"));
        assertTrue(VariableNameCodec.isEncoded("var_x"));
        assertFalse(VariableNameCodec.isEncoded("x"));
        assertThrows(IllegalArgumentException.class, () -> VariableNameCodec.decode("x"));
    }
}
