package de.dhbw.rahmlab.casadimaxima.api;

import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;

public class CasADiLaTeXifier {

    public static void main(String[] args) {
        // Example for insufficient simplifcation. Was simplified before.
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
        String maximaExpr = new ToMaximaTranspilerService().casadiToMaxima(casadiExpr);
        System.out.println(maximaExpr);
        maximaExpr = MaximaSimplifier.simplify(maximaExpr);
        System.out.println(maximaExpr);
        String latexExpr = MaximaLaTeXifier.LaTeXify(maximaExpr);
        System.out.println(latexExpr);
    }
}
