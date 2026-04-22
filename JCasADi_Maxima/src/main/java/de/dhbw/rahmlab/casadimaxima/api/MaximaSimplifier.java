package de.dhbw.rahmlab.casadimaxima.api;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class MaximaSimplifier {

    public static synchronized SX simplifySparsify(SX expr, List<SX> variables) {
        System.out.flush();
        System.out.println();

        SX casadiIn = SxStatic.sparsify(SxStatic.simplify(expr)); // To compare, what Maxima does better.
        System.out.println("->casadiIn: " + casadiIn.toString());

        String maximaIn = new ToMaximaTranspilerService().casadiToMaxima(casadiIn);
        System.out.println("->maximaIn: " + maximaIn);

        String maximaOut = MaximaSimplifier.simplify(maximaIn);
        System.out.println("->maximaOut: " + maximaOut);

        // sparsify happens here.
        SX casadiOut = new ToCasadiTranspilerService().maximaToCasadi(maximaOut, variables);
        System.out.println("->casadiOut: " + casadiOut.toString());

        // validate(casadiIn, casadiOut, variables);

        System.out.println();
        System.out.flush();

        return casadiOut;
    }

    /**
     * Only for debugging.
     */
    @Deprecated
    public static String simplify(String expr) throws RuntimeException {
        try {
            String maximaInput = "";
            maximaInput += "display2d:false$\n"; // Do not visualize formulas
            maximaInput += expr + "\n"; // Add expr
            maximaInput += "vs : (ev(%, infeval, trigrat, radscan))$\n"; // Simplify
            maximaInput += "optimize(%)$\n"; // common subexpression elimination
            maximaInput += "string(%);"; // Print result as single line. No line wrapping.
            // maximaInput += "tex(%);"; // Caution: Line Wrapping

            ProcessBuilder pb = new ProcessBuilder(
                "maxima",
                "--very-quiet",
                "--batch-string",
                maximaInput
            );
            Process p = pb.start();
            String out = new BufferedReader(new InputStreamReader(p.getInputStream()))
                .lines().toList().getLast();
            //.lines().collect(Collectors.joining("\n"));
            String err = new BufferedReader(new InputStreamReader(p.getErrorStream()))
                .lines().collect(Collectors.joining("\n"));
            // Does often not work!
            if (!err.isBlank()) {
                throw new RuntimeException("Maxima error: " + err);
            }
            int ret = p.waitFor();
            // Does often not work!
            if (ret != 0) {
                throw new RuntimeException("Maxima error");
            }
            return out;
        } catch (Exception e) {
            throw new RuntimeException("Failed to run Maxima", e);
        }
    }

    public static void validate(SX casadiIn, SX casadiOut, List<SX> variables) throws RuntimeException {
        if (casadiIn.columns() != casadiOut.columns()) {
            throw new AssertionError("Column count differs.");
        }
        if (casadiIn.rows() != casadiOut.rows()) {
            throw new AssertionError("Row count differs.");
        }

        SX validatorIn = SxStatic.minus(casadiIn, casadiOut);
        String maximaValIn = new ToMaximaTranspilerService().casadiToMaxima(validatorIn);
        String maximaValOut = MaximaSimplifier.simplify(maximaValIn);
        SX casadiValOut = new ToCasadiTranspilerService().maximaToCasadi(maximaValOut, variables);
        System.out.println("->casadiValOut: " + casadiValOut);

        if (casadiValOut.nnz_() != 0) {
            System.out.println();
            System.out.flush();
            throw new RuntimeException(String.format("Nonzeros in validation: %s", casadiValOut));
        }
    }
}
