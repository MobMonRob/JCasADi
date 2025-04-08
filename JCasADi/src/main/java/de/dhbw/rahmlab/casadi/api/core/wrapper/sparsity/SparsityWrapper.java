package de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity;

import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

public class SparsityWrapper {

    public final Sparsity sparsity;

    public SparsityWrapper(Sparsity sparsity) {
        this.sparsity = new Sparsity(sparsity);
    }

    public Sparsity getCasADiObject() {
        return this.sparsity;
    }

}
