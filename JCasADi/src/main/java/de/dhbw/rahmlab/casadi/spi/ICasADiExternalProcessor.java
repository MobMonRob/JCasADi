package de.dhbw.rahmlab.casadi.spi;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import java.util.List;

public interface ICasADiExternalProcessor {

    default SX simplifySparsify(SX expr, List<SX> variables) {
        SX simple = SxStatic.simplify(expr);
        SX sparse = SxStatic.sparsify(simple);
        return sparse;
    }

    String LaTeXify(SX casadiIn);
}
