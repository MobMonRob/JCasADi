package de.dhbw.rahmlab.casadimaxima.api;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MaximaLaTeXifier {

    public static synchronized String LaTeXify(SX casadiIn) {
        System.out.flush();
        System.out.println();

        System.out.println("->casadiIn: " + casadiIn.toString());

        String maximaIn = new ToMaximaTranspilerService().casadiToMaxima(casadiIn);
        System.out.println("->maximaIn: " + maximaIn);

        String maximaOut = MaximaLaTeXifier.LaTeXify(maximaIn);
        System.out.println("->maximaOut: " + maximaOut);

        System.out.println();
        System.out.flush();

        return maximaOut;
    }

    /**
     * Only for debugging.
     */
    @Deprecated
    public static String LaTeXify(String expr) throws RuntimeException {
        try {
            String maximaInput = "";
            maximaInput += "display2d:false$\n"; // Do not visualize formulas
            maximaInput += expr + "\n"; // Add expr
            maximaInput += "tex(%, false);"; // Print as tex and string

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
}
