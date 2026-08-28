package de.dhbw.rahmlab.casadimaxima.implementation.maxima;

import de.dhbw.rahmlab.casadi.SxStatic;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.CasadiToMaximaTranspilerService;
import de.dhbw.rahmlab.casadimaxima.implementation.transpilation.MaximaToCasadiTranspilerService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Internal Maxima simplification implementation; not a stable API.
 */
public final class MaximaSimplifier {

    private static final String FULL_SIMPLIFY_RESOURCE
        = "/de/dhbw/rahmlab/casadimaxima/full_simplify.mac";
    private static final Map<String, Path> CACHED_MAXIMA_FILES = new HashMap<>();
    private static final String RESULT_BEGIN = "__RESULT_BEGIN__";
    private static final String RESULT_END = "__RESULT_END__";

    public static SX simplify_pipeline(SX expression, List<SX> variables) {
        StringBuilder print = new StringBuilder();
        print.append("\n");
        SX casadiIn = SxStatic.sparsify(SxStatic.simplify(expression));
        print.append("->casadiIn: ").append(casadiIn);
        print.append("\n");
        String maximaIn = new CasadiToMaximaTranspilerService().casadiToMaxima(casadiIn);
        print.append("->maximaIn: ").append(maximaIn);
        print.append("\n");
        System.out.println(print); // Intentional diagnostic output during stabilization.
        String maximaOut = MaximaSimplifier.simplify_internal(maximaIn);
        print.append("->maximaOut: ").append(maximaOut);
        print.append("\n");
        SX casadiOut;
        try {
            casadiOut = new MaximaToCasadiTranspilerService().maximaToCasadi(maximaOut, variables);
        } catch (RuntimeException ex) {
            throw new RuntimeException(String.format("%s\nFrom Maxima in:\n%s", ex.getMessage(), maximaIn), ex);
        }
        print.append("->casadiOut: ").append(casadiOut);
        print.append("\n");
        synchronized (System.out) {
            System.out.flush();
            System.out.println(print);
            System.out.flush();
        }
        return casadiOut;
    }

    public static String simplify_internal(String maximaExpr) throws RuntimeException {
        String maximaInput = "";
        maximaInput += "display2d:false$\n"; // Do not visualize formulas
        maximaInput += "ratprint:false$\n"; // Do not visualize formulas
        // maximaInput += "load(" + maximaString(maximaFileCacher(FULL_SIMPLIFY_RESOURCE)) + ")$\n"; // Only for full_simplify
        maximaInput += maximaExpr + "\n"; // Add expr
        // maximaInput += "vs : (ev(%, infeval, trigrat))$\n"; // Old simplify
        // maximaInput += "vs: full_simplify(%)$\n"; // Simplify
        maximaInput += "vs: trigreduce(ratsimp(trigsimp(ratsimp(xthru(%)))))$\n"; // Simplify
        // maximaInput += "vo: %$\n";
        maximaInput += "vo: optimize(%)$\n"; // common subexpression elimination
        maximaInput += "printf(true,\"" + RESULT_BEGIN + "~%~a~%" + RESULT_END + "~%\", string(vo))$\n";
        return MaximaProcessExecutor.execute(maximaInput, RESULT_BEGIN, RESULT_END);
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
}
