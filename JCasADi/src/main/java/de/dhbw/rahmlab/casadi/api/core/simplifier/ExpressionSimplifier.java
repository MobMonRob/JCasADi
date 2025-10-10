package de.dhbw.rahmlab.casadi.api.core.simplifier;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;

/**
 * The ExpressionSimplifier interface defines methods for simplifying symbolic expressions.
 * Implementations of this interface provide functionality to simplify expressions represented
 * as strings or MXWrapper objects, potentially using different algorithms or systems.
 */
public interface ExpressionSimplifier {

    /**
     * Simplifies a given expression string and returns the simplified result as a string.
     * Implementations may use various techniques or external systems to achieve simplification.
     *
     * Note: This method is currently experimental and may exhibit unexpected behavior.
     * Users are encouraged to provide feedback to improve its reliability and performance
     *
     * @param expr the expression string to be simplified
     * @return the simplified expression string
     * @throws SimplificationException if an error occurs during the simplification process
     */
    String simplify(String expr) throws SimplificationException;

    /**
     * Simplifies a given MXWrapper expression and returns a new MXWrapper containing
     * the simplified expression. Implementations may convert the expression to a string,
     * simplify it, and then reconstruct the MXWrapper.
     *
     * @param expr the MXWrapper expression to be simplified
     * @return a new MXWrapper containing the simplified expression
     * @throws SimplificationException if an error occurs during the simplification process
     */
    MXWrapper simplify(MXWrapper expr) throws SimplificationException;

}
