package de.dhbw.rahmlab.casadi.api.core.simplifier;

/**
 * Thrown when an error occurs during symbolic simplification.
 */
public class SimplificationException extends RuntimeException {
    public SimplificationException(String msg, Throwable cause) { super(msg,cause); }
}
