package de.dhbw.rahmlab.casadimaxima.experiments;

import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.CasadiToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.implementation.maxima.MaximaLatexRenderer;
import de.dhbw.rahmlab.casadimaxima.implementation.maxima.MaximaSimplifier;
import java.util.HashSet;
import java.util.List;

public class TrigExperiment {

    public static void main(String[] args) {
        String casadiExpr = """
@1=0.0892001,
 @2=(0.5*arg0_0),
 @3=sq(sin(@2)),
 @4=sq(cos(@2)),
 @5=1,
 [00,
 00,
 00,
 00,
 00,
 ((@1*@3)+(@1*@4)),
 00,
 00,
 00,
 ((@5*@3)+(@5*@4)),
 00,
 00,
 00,
 00,
 00,
 00]
""";
        String maximaExpr = new CasadiToMaximaTranspilerService().casadiToMaxima(casadiExpr,
            new HashSet<>(List.of("arg0_0")));
        System.out.println(maximaExpr);
        maximaExpr = MaximaSimplifier.simplify_internal(maximaExpr);
        System.out.println(maximaExpr);
        String latexExpr = MaximaLatexRenderer.render_internal(maximaExpr);
        System.out.println(latexExpr);
    }
}
