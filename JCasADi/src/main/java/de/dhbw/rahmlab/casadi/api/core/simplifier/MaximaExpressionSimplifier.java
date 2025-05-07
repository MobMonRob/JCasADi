package de.dhbw.rahmlab.casadi.api.core.simplifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class MaximaExpressionSimplifier implements ExpressionSimplifier {

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
}