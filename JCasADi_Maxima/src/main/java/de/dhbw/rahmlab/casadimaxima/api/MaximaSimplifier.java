package de.dhbw.rahmlab.casadimaxima.api;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.casaditomaxima.ToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.maximatocasadi.ToCasadiTranspilerService;
import de.dhbw.rahmlab.casadimaxima.util.ProcessOutputReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaximaSimplifier {

    private static final String FULL_SIMPLIFY_RESOURCE
        = "/de/dhbw/rahmlab/casadimaxima/full_simplify.mac";
    private static final Map<String, Path> CACHED_MAXIMA_FILES = new HashMap<>();

    public static SX simplifySparsify(SX expr, List<SX> variables) {
        StringBuilder print = new StringBuilder();

        print.append("\n");
        SX casadiIn = SxStatic.sparsify(SxStatic.simplify(expr)); // To compare, what Maxima does better.
        print.append("->casadiIn: ").append(casadiIn.toString());
        print.append("\n");

        String maximaIn = new ToMaximaTranspilerService().casadiToMaxima(casadiIn);
        print.append("->maximaIn: ").append(maximaIn);
        print.append("\n");
        System.out.println(print.toString()); // Debug

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
            maximaInput += "ratprint:false$\n"; // Do not visualize formulas
            maximaInput += "load(" + maximaString(maximaFileCacher(FULL_SIMPLIFY_RESOURCE)) + ")$\n";
            maximaInput += maximaExpr + "\n"; // Add expr
            maximaInput += "vs: full_simplify_fast(%)$\n"; // Simplify
            maximaInput += "vo: optimize(%)$\n"; // common subexpression elimination
            maximaInput += "printf(true,\"__RESULT_BEGIN__~%~a~%__RESULT_END__~%\", string(vo))$\n"; // Print result as single line. No line wrapping.
            //maximaInput += "string(%);"; // Print result as single line. No line wrapping.

            ProcessBuilder pb = new ProcessBuilder(
                "maxima",
                "--very-quiet",
                "--batch-string",
                maximaInput
            );
            Process p = pb.start();

            ProcessOutputReader.Result result;
            try (var reader = new ProcessOutputReader(p)) {
                result = reader.await();
            }
            if (result.exitCode() != 0) {
                throw new RuntimeException(
                    "Maxima error:\n" + result.stderr()
                );
            }

            return extractMaximaResult(result.stdout());
        } catch (Exception e) {
            throw new RuntimeException("Failed to run Maxima", e);
        }
    }

    private static String extractMaximaResult(String out) {
        String begin = "__RESULT_BEGIN__";
        String end = "__RESULT_END__";

        // lastIndexOf, because --batch-string echoes the printf command itself.
        int start = out.lastIndexOf(begin);
        if (start < 0) {
            throw new RuntimeException("Maxima result start marker not found:\n" + out);
        }

        start += begin.length();

        int finish = out.indexOf(end, start);
        if (finish < 0) {
            throw new RuntimeException("Maxima result end marker not found:\n" + out);
        }

        return out.substring(start, finish).trim();
    }

    private static synchronized Path maximaFileCacher(String resourcePath) {
        Path cachedResource = CACHED_MAXIMA_FILES.get(resourcePath);
        if (cachedResource != null) {
            return cachedResource;
        }

        try (InputStream resource = MaximaSimplifier.class.getResourceAsStream(resourcePath)) {
            if (resource == null) {
                throw new RuntimeException("Missing Maxima resource: " + resourcePath);
            }

            Path resourceFileName = Path.of(resourcePath).getFileName();
            String resourceName = resourceFileName.toString();
            int extensionStart = resourceName.lastIndexOf('.');
            String prefix = resourceName.substring(0, extensionStart) + "-";
            String suffix = resourceName.substring(extensionStart);
            Path script = Files.createTempFile(prefix, suffix);
            try {
                Files.copy(resource, script, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                Files.deleteIfExists(script);
                throw e;
            }
            script.toFile().deleteOnExit();
            CACHED_MAXIMA_FILES.put(resourcePath, script);
            return script;
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract Maxima resource: " + resourcePath, e);
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
