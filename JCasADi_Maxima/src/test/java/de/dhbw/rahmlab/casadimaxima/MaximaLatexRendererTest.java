package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadimaxima.implementation.maxima.MaximaLatexRenderer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaximaLatexRendererTest {

    @Test
    void pipelineReturnsOriginalCasadiVariableNames() {
        String latex = MaximaLatexRenderer.render_pipeline(SxStatic.sym("simp"));
        assertTrue(latex.contains("simp"));
        assertFalse(latex.contains("var\\_simp"));
    }
}
