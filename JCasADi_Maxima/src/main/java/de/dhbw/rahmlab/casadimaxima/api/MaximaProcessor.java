package de.dhbw.rahmlab.casadimaxima.api;

import com.google.auto.service.AutoService;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.implementation.maxima.MaximaLatexRenderer;
import de.dhbw.rahmlab.casadi.spi.ICasADiExternalProcessor;
import de.dhbw.rahmlab.casadimaxima.implementation.maxima.MaximaSimplifier;
import java.util.List;

@AutoService(ICasADiExternalProcessor.class)
public class MaximaProcessor implements ICasADiExternalProcessor {

    @Override
    public SX simplifySparsify(SX expr, List<SX> variables) {
        return MaximaSimplifier.simplify_pipeline(expr, variables);
    }

    @Override
    public String LaTeXify(SX casadiIn) {
        return MaximaLatexRenderer.render_pipeline(casadiIn);
    }
}
