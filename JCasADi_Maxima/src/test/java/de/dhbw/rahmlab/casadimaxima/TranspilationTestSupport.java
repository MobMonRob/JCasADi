package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.TranspilationException.Phase;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.CasadiToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.MaximaToCasadiTranspilerService;
import java.util.List;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class TranspilationTestSupport {

    final CasadiToMaximaTranspilerService toMaxima = new CasadiToMaximaTranspilerService();
    final MaximaToCasadiTranspilerService toCasadi = new MaximaToCasadiTranspilerService();

    final SX symbol(String name) {
        return SxStatic.sym(name);
    }

    final void assertFailure(Executable operation, Direction direction, Phase phase, String source) {
        TranspilationException exception = assertThrows(TranspilationException.class, operation);
        assertEquals(direction, exception.getDirection());
        assertEquals(phase, exception.getPhase());
        assertTrue(exception.getSourceContext().contains(source));
    }

    final List<SX> symbols(String... names) {
        return java.util.Arrays.stream(names).map(this::symbol).toList();
    }
}
