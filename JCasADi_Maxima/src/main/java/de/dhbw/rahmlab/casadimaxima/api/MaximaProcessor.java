package de.dhbw.rahmlab.casadimaxima.api;

import com.google.auto.service.AutoService;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.spi.ICasADiExternalProcessor;
import java.util.List;

@AutoService(ICasADiExternalProcessor.class)
public class MaximaProcessor implements ICasADiExternalProcessor {

    @Override
    public SX simplify(SX expr, List<SX> variables) {
        return MaximaSimplifier.simplify(expr, variables);
    }

}
