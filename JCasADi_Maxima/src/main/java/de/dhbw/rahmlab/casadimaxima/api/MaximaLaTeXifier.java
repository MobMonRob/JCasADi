package de.dhbw.rahmlab.casadimaxima.api;

import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MaximaLaTeXifier {

    public static void main(String[] args) {
        String maximaIn
            = """
v1 : 0.5$
v2 : (v1 * (arg0_0)^2)$
v3 : (v2 + v1)$
v4 : (arg0_0 - (0.0996 * arg1_0))$
v5 : (v2 - v1)$
v6 : (v1 * (v4)^2)$
v7 : (v6 + v1)$
v8 : (v6 - v1)$
v9 : ((((v3 * v4) - (v5 * v4)) - (arg0_0 * v7)) + (arg0_0 * v8))$
v10 : (((((arg0_0 * v4) + (v3 * v7)) - (v5 * v7)) + (v3 * v8)) - (v5 * v8))$
vn : [v9, 0, 0, 0, v10, v10, 0, 0, 0, 0, 0, 0, 0, 0, 0, v9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]$
""";

        String latexified = LaTeXify(maximaIn);
        System.out.println(latexified);
    }

    public static String LaTeXify(SX casadiIn) {
        StringBuilder print = new StringBuilder();

        print.append("\n");
        print.append("->casadiIn: ").append(casadiIn.toString());
        print.append("\n");

        String maximaIn = new ToMaximaTranspilerService().casadiToMaxima(casadiIn);
        print.append("->maximaIn: ").append(maximaIn);
        print.append("\n");

        String maximaOut = MaximaLaTeXifier.LaTeXify(maximaIn);
        print.append("->maximaOut: ").append(maximaOut);
        print.append("\n");

        synchronized (System.out) {
            System.out.flush();
            System.out.println(print.toString());
            System.out.flush();
        }

        return maximaOut;
    }

    /**
     * Only for debugging.
     */
    @Deprecated
    public static String LaTeXify(String maximaExpr) throws RuntimeException {
        try {
            String maximaInput = "";
            maximaInput += "display2d:false$\n"; // Do not visualize formulas
            maximaInput += maximaExpr + "\n"; // Add expr
            maximaInput += "transpose(matrix(%))$\n"; // Column vector
            maximaInput += "tex_raw: tex1(%)$\n"; // tex single line
            maximaInput += "short_tex: ssubst(\"\\\\begin{pmatrix}\", \"\\\\ifx\\\\endpmatrix\\\\undefined\\\\pmatrix{\\\\else\\\\begin{pmatrix}\\\\fi \", tex_raw)$\n";
            maximaInput += "short_tex: ssubst(\"\\\\end{pmatrix}\", \"\\\\ifx\\\\endpmatrix\\\\undefined}\\\\else\\\\end{pmatrix}\\\\fi\", short_tex)$\n";
            maximaInput += "?princ(short_tex)$\n"; // Print

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
