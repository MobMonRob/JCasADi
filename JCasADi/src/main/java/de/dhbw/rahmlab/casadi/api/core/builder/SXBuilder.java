package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.MatrixBuilder;
import de.dhbw.rahmlab.casadi.api.core.wrapper.std.DoubleVector;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sx.SXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;

public class SXBuilder implements MatrixBuilder<SXBuilder> {
    private double value;
    private double[] values;
    private long rows;
    private long cols;
    private String name;
    private Sparsity sparsity;
    private long identitySize;

    @Override
    public SXBuilder setValue(double value) {
        this.value = value;
        this.values = null;
        return this;
    }

    @Override
    public SXBuilder setValues(double... values) {
        this.values = values;
        this.value = Double.NaN;
        return this;
    }

    @Override
    public SXBuilder setDimensions(long rows, long cols) {
        this.rows = rows;
        this.cols = cols;
        return this;
    }

    @Override
    public SXBuilder setRows(long rows) {
        this.rows = rows;
        this.cols = 1;
        return this;
    }

    @Override
    public SXBuilder setCols(long cols) {
        this.cols = cols;
        this.rows = 1;
        return this;
    }

    @Override
    public SXBuilder setIdentitySize(long identitySize) {
        this.identitySize = identitySize;
        return this;
    }

    @Override
    public SXBuilder setSparsity(Sparsity sparsity) {
        this.sparsity = sparsity;
        return this;
    }

    @Override
    public SXBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public SXWrapper buildFromValues() {
        if (values == null || values.length == 0) {
            return new SXWrapper(value);
        } else {
            return new SXWrapper(new DoubleVector(values));
        }
    }

    @Override
    public SXWrapper buildSymbolic() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must be set for symbolic SX.");
        }
        if (rows > 0 && cols > 0) {
            return SXWrapper.sym(name, rows, cols);
        } else if (rows > 0) {
            return SXWrapper.sym(name, rows);
        } else if (sparsity != null) {
            return SXWrapper.sym(name, sparsity);
        } else {
            return SXWrapper.sym(name);
        }
    }

    @Override
    public SXWrapper buildOnes() {
        if (rows > 0 && cols > 0) {
            return SXWrapper.ones(rows, cols);
        } else if (rows > 0) {
            return SXWrapper.ones(rows);
        } else if (sparsity != null) {
            return SXWrapper.ones(sparsity);
        } else {
            return SXWrapper.ones();
        }
    }

    @Override
    public SXWrapper buildNaN() {
        if (rows > 0 && cols > 0) {
            return SXWrapper.nan(rows, cols);
        } else if (rows > 0) {
            return SXWrapper.nan(rows);
        } else if (sparsity != null) {
            return SXWrapper.nan(sparsity);
        } else {
            return SXWrapper.nan();
        }
    }

    @Override
    public SXWrapper buildInf() {
        if (rows > 0 && cols > 0) {
            return SXWrapper.inf(rows, cols);
        } else if (rows > 0) {
            return SXWrapper.inf(rows);
        } else if (sparsity != null) {
            return SXWrapper.inf(sparsity);
        } else {
            return SXWrapper.inf();
        }
    }

    public SXWrapper buildRandom() {
        if (rows > 0 && cols > 0) {
            return SXWrapper.rand(rows, cols);
        } else if (rows > 0) {
            return SXWrapper.rand(rows);
        } else if (sparsity != null) {
            return SXWrapper.rand(sparsity);
        } else {
            return SXWrapper.rand();
        }
    }

    @Override
    public SXWrapper buildZeroMatrix() {
        if (rows > 0 && cols > 0) {
            return SXWrapper.zeros(rows, cols);
        } else if (rows > 0) {
            return SXWrapper.zeros(rows);
        } else if (sparsity != null) {
            return SXWrapper.zeros(sparsity);
        } else {
            return SXWrapper.zeros();
        }
    }

    @Override
    public SXWrapper buildIdentityMatrix() {
        return SXWrapper.eye(identitySize);
    }

    @Override
    public SXWrapper buildSparseMatrix() {
        if (sparsity == null) {
            throw new IllegalArgumentException("Sparsity must be set for identity matrix.");
        }
        return new SXWrapper(sparsity);
    }

    @Override
    public SXWrapper build() {
        if (sparsity != null && values != null) {
            return new SXWrapper(sparsity, buildFromValues());
        }
        if (sparsity != null) {
            return new SXWrapper(sparsity);
        }
        if (values != null) {
            return new SXWrapper(new DoubleVector(values));
        }
        if (rows > 0 && cols > 0) {
            return new SXWrapper(rows, cols);
        }
        if (rows > 0) {
            this.cols = 1;
            return new SXWrapper(rows, cols);
        }
        if (cols > 0) {
            this.rows = 1;
            return new SXWrapper(rows, cols);
        }
        if (rows > 0 && cols > 0 && rows == cols) {
            return SXWrapper.eye(rows);
        }
        return new SXWrapper();
    }

}
