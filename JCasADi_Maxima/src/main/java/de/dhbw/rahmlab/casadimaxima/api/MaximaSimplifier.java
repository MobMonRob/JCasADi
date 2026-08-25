package de.dhbw.rahmlab.casadimaxima.api;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

public class MaximaSimplifier {

    private static final String FULL_SIMPLIFY_FAST_RESOURCE
        = "/de/dhbw/rahmlab/casadimaxima/full_simplify_fast.mac";
    private static Path fullSimplifyFastScript;

    public static SX simplifySparsify(SX expr, List<SX> variables) {
        StringBuilder print = new StringBuilder();

        print.append("\n");
        SX casadiIn = SxStatic.sparsify(SxStatic.simplify(expr)); // To compare, what Maxima does better.
        print.append("->casadiIn: ").append(casadiIn.toString());
        print.append("\n");

        String maximaIn = new ToMaximaTranspilerService().casadiToMaxima(casadiIn);
        print.append("->maximaIn: ").append(maximaIn);
        print.append("\n");

        String maximaOut = MaximaSimplifier.simplify(maximaIn);
        print.append("->maximaOut: ").append(maximaOut);
        print.append("\n");

        // sparsify happens here.
        SX casadiOut = new ToCasadiTranspilerService().maximaToCasadi(maximaOut, variables);
        print.append("->casadiOut: ").append(casadiOut.toString());
        print.append("\n");

        // validate(casadiIn, casadiOut, variables);
        synchronized (System.out) {
            System.out.flush();
            System.out.println(print.toString());
            System.out.flush();
        }

        return casadiOut;
    }

    /**
     * Only for debugging.
     */
    @Deprecated
    public static String simplify(String maximaExpr) throws RuntimeException {
        try {
            String maximaInput = "";
            maximaInput += "display2d:false$\n"; // Do not visualize formulas
            maximaInput += "load(" + maximaString(fullSimplifyFastScript()) + ")$\n";
            maximaInput += maximaExpr + "\n"; // Add expr
            maximaInput += "vs : full_simplify_fast(%)$\n"; // Simplify
            maximaInput += "optimize(%)$\n"; // common subexpression elimination
            maximaInput += "string(%);"; // Print result as single line. No line wrapping.

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

    private static synchronized Path fullSimplifyFastScript() {
        if (fullSimplifyFastScript != null) {
            return fullSimplifyFastScript;
        }

        try (InputStream resource = MaximaSimplifier.class.getResourceAsStream(FULL_SIMPLIFY_FAST_RESOURCE)) {
            if (resource == null) {
                throw new RuntimeException("Missing Maxima resource: " + FULL_SIMPLIFY_FAST_RESOURCE);
            }

            Path script = Files.createTempFile("full_simplify_fast-", ".mac");
            try {
                Files.copy(resource, script, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Files.deleteIfExists(script);
                throw e;
            }
            script.toFile().deleteOnExit();
            fullSimplifyFastScript = script;
            return script;
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract Maxima resource: " + FULL_SIMPLIFY_FAST_RESOURCE, e);
        }
    }

    private static String maximaString(Path path) {
        return "\"" + path.toAbsolutePath().toString()
            .replace("\\", "\\\\")
            .replace("\"", "\\\"") + "\"";
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
