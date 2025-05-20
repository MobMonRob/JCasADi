package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.api.core.wrapper.numeric.NumberWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.OptiCallback;

/**
 * Represents a callback mechanism for NLP (Non-Linear Programming) processes.
 * This class provides methods to interact with and execute callbacks during optimization iterations.
 */
public class NLPCallback {

    private final OptiCallback optiCallback;

    /**
     * Constructs an NLPCallback with a new OptiCallback instance.
     */
    public NLPCallback() {
        this.optiCallback = new OptiCallback();
    }

    /**
     * Constructs an NLPCallback using an existing OptiCallback object.
     *
     * @param other the OptiCallback object used to initialize the callback.
     */
    public NLPCallback(OptiCallback other) {
        this.optiCallback = new OptiCallback(other);
    }

    /**
     * Constructs an NLPCallback using another NLPCallback object.
     *
     * @param other the NLPCallback object used to initialize the callback.
     */
    public NLPCallback(NLPCallback other) {
        this.optiCallback = new OptiCallback(other.getCasADiObject());
    }

    /**
     * Executes the callback for a given iteration.
     *
     * @param iteration the iteration number for which the callback is executed.
     */
    public void call(long iteration) {
        this.optiCallback.call(iteration);
    }

    /**
     * Executes the callback for a given iteration, specified by a NumberWrapper.
     *
     * @param iteration the NumberWrapper containing the iteration number for which the callback is executed.
     */
    public void call(NumberWrapper iteration) {
        this.optiCallback.call(iteration.getLongValue());
    }

    /**
     * Retrieves the underlying CasADi object.
     *
     * @return the OptiCallback object.
     */
    public OptiCallback getCasADiObject() {
        return this.optiCallback;
    }
}
