package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.MXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

public class MXBuilder {
    private double value;
    private long rows;
    private long cols;
    private Sparsity sparsity;
    private String name;

    public MXBuilder setValue(double value) {
        this.value = value;
        return this;
    }

    public MXBuilder setDimensions(long rows, long cols) {
        this.rows = rows;
        this.cols = cols;
        return this;
    }

    public MXBuilder setSparsity(Sparsity sparsity) {
        this.sparsity = sparsity;
        return this;
    }

    public MXBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MXWrapper buildConstant() {
        return new MXWrapper(new MX(value));
    }

    public MXWrapper buildSymbolic() {
        return new MXWrapper(MX.sym(name, rows, cols));
    }

    public MXWrapper buildZeroMatrix() {
        return new MXWrapper(MX.zeros(rows, cols));
    }

    public MXWrapper buildIdentityMatrix() {
        return new MXWrapper(MX.eye(rows));
    }

    public MXWrapper buildSparseMatrix() {
        return new MXWrapper(new MX(sparsity));
    }

    public MXWrapper build() {
        if (name != null && !name.isEmpty()) {
            return buildSymbolic();
        } else if (rows > 0 && cols > 0) {
            return buildZeroMatrix();
        }
        return buildConstant();
    }
}
