package de.dhbw.rahmlab.casadi.api.core.wrapper.mx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.interfaces.MatrixBuilder;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

import java.util.Arrays;
import java.util.List;

/**
 * Builder class for creating MXWrapper objects.
 */
public class MXBuilder implements MatrixBuilder<MXBuilder> {
    private Double value;
    private Double[] values;
    private Long rows;
    private Long cols;
    private SparsityWrapper sparsity;
    private String name;
    private Long identitySize;

    /**
     * Sets a single constant value for the MX object.
     *
     * @param value the constant value to set
     * @return the current instance of MXBuilder
     */
    @Override
    public MXBuilder setValue(double value) {
        this.value = value;
        this.values = null;
        return this;
    }

    /**
     * Sets an array of values for the MX object using varargs.
     *
     * @param values the variable number of double values to set
     * @return the current instance of MXBuilder
     */
    @Override
    public MXBuilder setValues(Number... values) {
        this.values = Arrays.stream(values)
                .map(Number::doubleValue)
                .toArray(Double[]::new);
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
    @Override
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
    @Override
    public MXBuilder setRows(long rows) {
        this.rows = rows;
        this.cols = 1L;
        return this;
    }

    /**
     * Sets the number of columns for the MX object, treating it as a row vector.
     *
     * @param cols the number of columns
     * @return the current instance of MXBuilder
     */
    @Override
    public MXBuilder setCols(long cols) {
        this.cols = cols;
        this.rows = 1L;
        return this;
    }

    /**
     * Sets the size for the identity matrix.
     *
     * This method allows the user to specify the size of the identity matrix
     * that will be created. The identity matrix will be a square matrix with
     * the specified number of rows and columns, filled with ones on the diagonal
     * and zeros elsewhere.
     *
     * @param identitySize the size of the identity matrix to set
     * @return the current instance of MXBuilder, allowing for method chaining
     */
    @Override
    public MXBuilder setIdentitySize(long identitySize) {
        this.identitySize = identitySize;
        return this;
    }

    /**
     * Sets the sparsity structure for the MX object.
     *
     * @param sparsity the sparsity structure to set
     * @return the current instance of MXBuilder
     */
    @Override
    public MXBuilder setSparsity(SparsityWrapper sparsity) {
        this.sparsity = sparsity;
        return this;
    }

    /**
     * Sets the name for the symbolic MX object.
     *
     * @param name the name to set
     * @return the current instance of MXBuilder
     */
    @Override
    public MXBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Builds an MXWrapper from the specified values.
     *
     * @return an MXWrapper containing the constructed MX object
     */
    @Override
    @SuppressWarnings("unchecked")
    public MXWrapper buildFromValues() {
        if (values == null || values.length == 0) {
            return new MXWrapper(new MX(value));
        } else {
            return new MXWrapper(new MX(new StdVectorDouble(List.of(values))));
        }
    }

    /**
     * Builds a symbolic MXWrapper with the specified name and dimensions.
     *
     * This method creates a symbolic MX object using the provided name. The dimensions
     * of the matrix or vector can be defined by the number of rows and columns. If no
     * dimensions are specified, a symbolic scalar is created. If a sparsity structure is
     * defined, a symbolic matrix with the specified sparsity will be created.
     *
     * @return an MXWrapper containing the constructed symbolic MX object
     * @throws IllegalArgumentException if the name is not set or is empty
     */
    @Override
    @SuppressWarnings("unchecked")
    public MXWrapper buildSymbolic() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must be set for symbolic MX.");
        } else if (rows > 0 && cols > 0) {
            return MXWrapper.sym(name, rows, cols);
        } else if (rows > 0) {
            return MXWrapper.sym(name, rows);
        } else if (sparsity != null) {
            return MXWrapper.sym(name, sparsity);
        } else {
            return MXWrapper.sym(name);
        }
    }

    /**
     * Builds a matrix or vector filled with ones.
     *
     * This method creates a matrix with the specified number of rows and columns,
     * or a vector if only the number of rows is specified. If no dimensions are set,
     * a 1x1 matrix filled with one is created. If sparsity is defined, a matrix
     * with the specified sparsity structure will be filled with ones.
     *
     * @return a MXWrapper containing the constructed matrix or vector filled with ones
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildOnes() {
        if (rows > 0 && cols > 0) {
            return DMWrapper.ones(rows, cols);
        } else if (rows > 0) {
            return DMWrapper.ones(rows);
        } else if (sparsity != null) {
            return DMWrapper.ones(sparsity);
        } else {
            return DMWrapper.ones();
        }
    }

    /**
     * Builds a matrix or vector filled with NaN (Not a Number) values.
     *
     * This method creates a matrix with the specified number of rows and columns,
     * or a vector if only the number of rows is specified. If no dimensions are set,
     * a 1x1 matrix filled with NaN is created. If sparsity is defined, a matrix
     * with the specified sparsity structure will be filled with NaN values.
     *
     * @return a MXWrapper containing the constructed matrix or vector filled with NaN
     */
    @Override
    @SuppressWarnings("unchecked")
    public MXWrapper buildNaN() {
        if (rows > 0 && cols > 0) {
            return MXWrapper.nan(rows, cols);
        } else if (rows > 0) {
            return MXWrapper.nan(rows);
        } else if (sparsity != null) {
            return MXWrapper.nan(sparsity);
        } else {
            return MXWrapper.nan();
        }
    }

    /**
     * Builds a matrix or vector filled with Infinity values.
     *
     * This method creates a matrix with the specified number of rows and columns,
     * or a vector if only the number of rows is specified. If no dimensions are set,
     * a 1x1 matrix filled with Infinity is created. If sparsity is defined, a matrix
     * with the specified sparsity structure will be filled with Infinity values.
     *
     * @return a MXWrapper containing the constructed matrix or vector filled with Infinity
     */
    @Override
    @SuppressWarnings("unchecked")
    public MXWrapper buildInf() {
        if (rows > 0 && cols > 0) {
            return MXWrapper.inf(rows, cols);
        } else if (rows > 0) {
            return MXWrapper.inf(rows);
        } else if (sparsity != null) {
            return MXWrapper.inf(sparsity);
        } else {
            return MXWrapper.inf();
        }
    }

    /**
     * Builds a zero matrix MXWrapper with the specified dimensions.
     *
     * @return an MXWrapper containing the zero matrix
     */
    @Override
    @SuppressWarnings("unchecked")
    public MXWrapper buildZeroMatrix() {
        if (rows > 0 && cols > 0) {
            return MXWrapper.zeros(rows, cols);
        } else if (rows > 0) {
            return MXWrapper.zeros(rows);
        } else if (sparsity != null) {
            return MXWrapper.zeros(sparsity);
        } else {
            return MXWrapper.zeros();
        }
    }

    /**
     * Builds an identity matrix MXWrapper with the specified size.
     *
     * @return an MXWrapper containing the identity matrix
     */
    @Override
    @SuppressWarnings("unchecked")
    public MXWrapper buildIdentityMatrix() {
        return MXWrapper.eye(identitySize);
    }

    /**
     * Builds a sparse matrix MXWrapper based on the specified sparsity structure.
     *
     * @return an MXWrapper containing the sparse matrix
     * @throws IllegalStateException if sparsity is not set
     */
    @Override
    @SuppressWarnings("unchecked")
    public MXWrapper buildSparseMatrix() {
        if (sparsity == null) {
            throw new IllegalStateException("Sparsity must be set for a sparse matrix.");
        }
        return new MXWrapper(new MX(sparsity.getCasADiObject()));
    }

    /**
     * Builds an MXWrapper based on the current configuration of the builder.
     *
     * @return an MXWrapper containing the constructed MX object
     */
    @Override
    @SuppressWarnings("unchecked")
    public MXWrapper build() {
        if (sparsity != null && values != null) {
            return new MXWrapper(sparsity, buildFromValues());
        }
        if (sparsity != null) {
            return new MXWrapper(sparsity);
        }
        if (values != null) {
            return new MXWrapper(values);
        }
        if (rows > 0 && cols > 0) {
            return new MXWrapper(rows, cols);
        }
        if (rows > 0) {
            this.cols = 1L;
            return new MXWrapper(rows, cols);
        }
        if (cols > 0) {
            this.rows = 1L;
            return new MXWrapper(rows, cols);
        }
        if (rows > 0 && cols > 0 && rows == cols) {
            return MXWrapper.eye(rows);
        }
        return new MXWrapper();
    }

}
