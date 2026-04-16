package de.dhbw.rahmlab.casadimaxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.MaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.CasadiTranspilerService;
import java.util.List;

public class MaximaProcessor {

    public static synchronized SX simplify(SX expr, List<SX> variables) {
        System.out.flush();
        System.out.println();

        SX casadiIn = SxStatic.sparsify(SxStatic.simplify(expr)); // To compare, what Maxima does better.
        System.out.println("->casadiIn: " + casadiIn.toString());

        String maximaIn = new MaximaTranspilerService().casadiToMaxima(casadiIn);
        System.out.println("->maximaIn: " + maximaIn);

        String maximaOut = new MaximaSimplifier().simplify(maximaIn);
        System.out.println("->maximaOut: " + maximaOut);

        SX casadiOut = new CasadiTranspilerService().maximaToCasadi(maximaOut, variables);
        System.out.println("->casadiOut: " + casadiOut.toString());

        validate(casadiIn, casadiOut, variables);

        System.out.println();
        System.out.flush();

        return casadiOut;
    }

    private static void validate(SX casadiIn, SX casadiOut, List<SX> variables) throws RuntimeException {
        SX validatorIn = SxStatic.minus(casadiIn, casadiOut);
        String maximaValIn = new MaximaTranspilerService().casadiToMaxima(validatorIn);
        String maximaValOut = new MaximaSimplifier().simplify(maximaValIn);
        SX casadiValOut = new CasadiTranspilerService().maximaToCasadi(maximaValOut, variables);
        System.out.println("->casadiValOut: " + casadiValOut);

        if (casadiValOut.nnz_() != 0) {
            System.out.println();
            System.out.flush();
            throw new RuntimeException(String.format("Nonzeros in validation: %s", casadiValOut));
        }
    }
}
