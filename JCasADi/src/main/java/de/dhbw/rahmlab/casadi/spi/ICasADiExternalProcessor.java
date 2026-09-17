package de.dhbw.rahmlab.casadi.spi;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import java.util.List;

public interface ICasADiExternalProcessor {

    SX simplifySparsify(SX expr, List<SX> variables, String nameOfPi);

    String LaTeXify(SX casadiIn);
}
