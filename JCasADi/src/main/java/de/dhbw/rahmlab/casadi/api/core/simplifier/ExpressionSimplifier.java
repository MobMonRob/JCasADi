package de.dhbw.rahmlab.casadi.api.core.simplifier;

public interface ExpressionSimplifier {
    String simplify(String expr) throws SimplificationException;
}
