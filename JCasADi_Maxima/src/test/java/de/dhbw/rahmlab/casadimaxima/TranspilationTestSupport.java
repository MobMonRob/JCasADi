package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Direction;
import de.dhbw.rahmlab.casadimaxima.api.TranspilationException.Phase;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService;
import java.util.List;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class TranspilationTestSupport {

    final ToMaximaTranspilerService toMaxima = new ToMaximaTranspilerService();
    final ToCasadiTranspilerService toCasadi = new ToCasadiTranspilerService();

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
