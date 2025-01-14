package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.MXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

/**
 * Builder class for creating MXWrapper objects.
 */
public class MXBuilder {
    private double value;
    private double[] values;
    private long rows;
    private long cols;
    private Sparsity sparsity;
    private String name;

    /**
     * Sets a single constant value for the MX object.
     *
     * @param value the constant value to set
     * @return the current instance of MXBuilder
     */
    public MXBuilder setValue(double value) {
        this.value = value;
        this.values = null;
        return this;
    }

    /**
     * Sets an array of values for the MX object.
     *
     * @param values the array of values to set
     * @return the current instance of MXBuilder
     */
//    public MXBuilder setValues(double[] values) {
//        this.values = values;
//        this.value = Double.NaN;
//        return this;
//    }

    /**
     * Sets an array of values for the MX object using varargs.
     *
     * @param values the variable number of double values to set
     * @return the current instance of MXBuilder
     */
    public MXBuilder setValues(double... values) {
        this.values = values;
        this.value = Double.NaN;
        return this;
    }

    /**
     * Sets the dimensions for the MX object.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return the current instance of MXBuilder
     */
    public MXBuilder setDimensions(long rows, long cols) {
        this.rows = rows;
        this.cols = cols;
        return this;
    }

    /**
     * Sets the number of rows for the MX object, treating it as a column vector.
     *
     * @param rows the number of rows
     * @return the current instance of MXBuilder
     */
    public MXBuilder setRows(long rows) {
        this.rows = rows;
        this.cols = 1;
        return this;
    }

    /**
     * Sets the number of columns for the MX object, treating it as a row vector.
     *
     * @param cols the number of columns
     * @return the current instance of MXBuilder
     */
    public MXBuilder setCols(long cols) {
        this.cols = cols;
        this.rows = 1;
        return this;
    }

    /**
     * Sets the sparsity structure for the MX object.
     *
     * @param sparsity the sparsity structure to set
     * @return the current instance of MXBuilder
     */
    public MXBuilder setSparsity(Sparsity sparsity) {
        this.sparsity = sparsity;
        return this;
    }

    /**
     * Sets the name for the symbolic MX object.
     *
     * @param name the name to set
     * @return the current instance of MXBuilder
     */
    public MXBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Builds an MXWrapper from the specified values.
     *
     * @return an MXWrapper containing the constructed MX object
     */
    private MXWrapper buildFromValues() {
        if (values == null || values.length == 0) {
            return new MXWrapper(new MX(value));
        } else {
            return new MXWrapper(new MX(new StdVectorDouble(values)));
        }
    }

    /**
     * Builds a symbolic MXWrapper with the specified name and dimensions.
     *
     * @return an MXWrapper containing the symbolic MX object
     */
    private MXWrapper buildSymbolic() {
        if (rows > 0 && cols > 0 && name != null && !name.isEmpty()) {
            return MXWrapper.sym(name, rows, cols);
        } else if (rows > 0 && name != null && !name.isEmpty()) {
            return MXWrapper.sym(name, rows);
        } else if (sparsity != null && name != null && !name.isEmpty()) {
            return MXWrapper.sym(name, sparsity);
        } else if (name != null && !name.isEmpty()) {
            return MXWrapper.sym(name);
        }
        return new MXWrapper();
    }

    /**
     * Builds a zero matrix MXWrapper with the specified dimensions.
     *
     * @return an MXWrapper containing the zero matrix
     */
    private MXWrapper buildZeroMatrix() {
        return new MXWrapper(MX.zeros(rows, cols));
    }

    /**
     * Builds an identity matrix MXWrapper with the specified size.
     *
     * @return an MXWrapper containing the identity matrix
     */
    private MXWrapper buildIdentityMatrix() {
        return new MXWrapper(MX.eye(rows));
    }

    /**
     * Builds a sparse matrix MXWrapper based on the specified sparsity structure.
     *
     * @return an MXWrapper containing the sparse matrix
     * @throws IllegalStateException if sparsity is not set
     */
    private MXWrapper buildSparseMatrix() {
        if (sparsity == null) {
            throw new IllegalStateException("Sparsity must be set for a sparse matrix.");
        }
        return new MXWrapper(new MX(sparsity));
    }

    /**
     * Builds an MXWrapper based on the current configuration of the builder.
     *
     * @return an MXWrapper containing the constructed MX object
     */
    public MXWrapper build() {
        if (name != null && !name.isEmpty()) {
            return buildSymbolic();
        } else if (rows > 0 && cols > 0) {
            return buildZeroMatrix();
        } else if (values != null) {
            return buildFromValues();
        } else if (rows > 0) {
            this.cols = 1;
            return buildSymbolic();
        } else if (cols > 0) {
            this.rows = 1;
            return buildSymbolic();
        } else if (rows > 0 && cols > 0 && rows == cols) {
            return buildIdentityMatrix();
        } else if (sparsity != null) {
            return buildSparseMatrix();
        }
        return buildFromValues();
    }
}
