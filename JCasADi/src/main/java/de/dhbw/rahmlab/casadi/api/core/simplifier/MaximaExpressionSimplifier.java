package de.dhbw.rahmlab.casadi.api.core.simplifier;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * The MaximaExpressionSimplifier class provides functionality to simplify
 * symbolic expressions using Maxima, a computer algebra system. It implements
 * the ExpressionSimplifier interface and offers methods to simplify expressions
 * represented as strings or MXWrapper objects.
 */
public class MaximaExpressionSimplifier implements ExpressionSimplifier {

    /**
     * Simplifies a given expression string using Maxima. This method executes
     * Maxima in batch mode to perform the simplification and returns the simplified
     * expression as a string.
     *
     * Note: This method is currently experimental and may exhibit unexpected behavior.
     * Users are encouraged to provide feedback to improve its reliability and performance.
     *
     * @param expr the expression string to be simplified
     * @return the simplified expression string
     * @throws SimplificationException if an error occurs during the simplification process
     */
    @Override
    public String simplify(String expr) throws SimplificationException {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "maxima",
                    "--very-quiet",
                    "--batch-string",
                    String.format("string(simplify(%s));", expr)
            );
            Process p = pb.start();
            String out = new BufferedReader(new InputStreamReader(p.getInputStream()))
                    .lines().collect(Collectors.joining())
                    .replace("\"","").trim();
            int exit = p.waitFor();
            if (exit != 0) {
                String err = new BufferedReader(new InputStreamReader(p.getErrorStream()))
                        .lines().collect(Collectors.joining("\n"));
                throw new SimplificationException("Maxima error: "+err,null);
            }
            return out;
        } catch (Exception e) {
            throw new SimplificationException("Failed to run Maxima", e);
        }
    }

    /**
     * Simplifies a CasADi MXWrapper expression by converting it to a string,
     * simplifying it using Maxima, and returning a new MXWrapper containing
     * the simplified expression.
     *
     * @param expr the symbolic MX expression to be simplified
     * @return a new MXWrapper containing the simplified expression
     * @throws SimplificationException if an error occurs during the simplification process
     */
    @Override
    public MXWrapper simplify(MXWrapper expr) throws SimplificationException {
        String raw = expr.toString();
        String simp = simplify(raw);
        return new MXWrapper(simp);
    }

}