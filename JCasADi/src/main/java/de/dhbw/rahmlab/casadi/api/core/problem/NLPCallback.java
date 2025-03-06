package de.dhbw.rahmlab.casadi.api.core.problem;

import de.dhbw.rahmlab.casadi.impl.casadi.OptiCallback;

public class NLPCallback {

    private final OptiCallback optiCallback;

    public NLPCallback() {
        this.optiCallback = new OptiCallback();
    }

    public NLPCallback(OptiCallback other) {
        this.optiCallback = new OptiCallback(other);
    }

    public NLPCallback(NLPCallback other) {
        this.optiCallback = new OptiCallback(other.getCasADiObject());
    }

    public void call(long iteration) {
        this.optiCallback.call(iteration);
    }

    public OptiCallback getCasADiObject() {
        return this.optiCallback;
    }

}
