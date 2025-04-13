package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.MatrixBuilder;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

import java.util.Arrays;
import java.util.List;

/**
 * Builder class for creating DMWrapper objects.
 */
public class DMBuilder implements MatrixBuilder<DMBuilder> {
    private Double value;
    private Double[] values;
    private Long rows;
    private Long cols;
    private String name;
    private SparsityWrapper sparsity;
    private Long identitySize;

    /**
     * Sets a single constant value for the DM object.
     *
     * @param value the constant value to set
     * @return the current instance of DMBuilder
     */
    @Override
    public DMBuilder setValue(double value) {
        this.value = value;
        this.values = null;
        return this;
    }

    /**
     * Sets an array of values for the DM object using varargs.
     *
     * @param values the variable number of double values to set
     * @return the current instance of DMBuilder
     */
    @Override
    public DMBuilder setValues(Number... values) {
        this.values = Arrays.stream(values)
                .map(Number::doubleValue)
                .toArray(Double[]::new);
        this.value = Double.NaN;
        return this;
    }

    /**
     * Sets the dimensions for the DM object.
     *
     * @param rows the number of rows
     * @param cols the number of columns
     * @return the current instance of DMBuilder
     */
    @Override
    public DMBuilder setDimensions(long rows, long cols) {
        this.rows = rows;
        this.cols = cols;
        return this;
    }

    /**
     * Sets the number of rows for the DM object, treating it as a column vector.
     *
     * @param rows the number of rows
     * @return the current instance of DMBuilder
     */
    @Override
    public DMBuilder setRows(long rows) {
        this.rows = rows;
        this.cols = 1L;
        return this;
    }

    /**
     * Sets the number of columns for the DM object, treating it as a row vector.
     *
     * @param cols the number of columns
     * @return the current instance of DMBuilder
     */
    @Override
    public DMBuilder setCols(long cols) {
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
     * @return the current instance of DMBuilder, allowing for method chaining
     */
    @Override
    public DMBuilder setIdentitySize(long identitySize) {
        this.identitySize = identitySize;
        return this;
    }

    /**
     * Sets the name for the symbolic DM object.
     *
     * @param name the name to set
     * @return the current instance of DMBuilder
     */
    @Override
    public DMBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the sparsity structure for the DM object.
     *
     * @param sparsity the sparsity structure to set
     * @return the current instance of DMBuilder
     */
    @Override
    public DMBuilder setSparsity(SparsityWrapper sparsity) {
        this.sparsity = sparsity;
        return this;
    }

    /**
     * Builds a DMWrapper from the specified values.
     *
     * @return a DMWrapper containing the constructed DM object
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildFromValues() {
        if (values != null && values.length > 0) {
            return new DMWrapper(new DM(new StdVectorDouble(List.of(values))));
        } else if (value != null) {
            return new DMWrapper(new DM(value));
        } else {
            throw new IllegalStateException("Either value or values must be set.");
        }
    }

    /**
     * Builds a symbolic DM object.
     * <p></p>
     * This method creates a symbolic matrix or vector with the specified name.
     * The dimensions of the matrix or vector can be defined by the number of rows
     * and columns. If no dimensions are specified, a symbolic scalar is created.
     * If sparsity is defined, a symbolic matrix with the specified sparsity structure
     * will be created.
     *
     * @return a DMWrapper containing the constructed symbolic DM object
     * @throws IllegalArgumentException if the name is not set
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildSymbolic() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must be set for symbolic DM.");
        }
        if (rows > 0 && cols > 0) {
            return DMWrapper.sym(name, rows, cols);
        } else if (rows > 0) {
            return DMWrapper.sym(name, rows);
        } else if (sparsity != null) {
            return DMWrapper.sym(name, sparsity.getCasADiObject());
        } else {
            return DMWrapper.sym(name);
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
     * @return a DMWrapper containing the constructed matrix or vector filled with ones
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildOnes() {
        if (rows > 0 && cols > 0) {
            return DMWrapper.ones(rows, cols);
        } else if (rows > 0) {
            return DMWrapper.ones(rows);
        } else if (sparsity != null) {
            return DMWrapper.ones(sparsity.getCasADiObject());
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
     * @return a DMWrapper containing the constructed matrix or vector filled with NaN
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildNaN() {
        if (rows > 0 && cols > 0) {
            return DMWrapper.nan(rows, cols);
        } else if (rows > 0) {
            return DMWrapper.nan(rows);
        } else if (sparsity != null) {
            return DMWrapper.nan(sparsity.getCasADiObject());
        } else {
            return DMWrapper.nan();
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
     * @return a DMWrapper containing the constructed matrix or vector filled with Infinity
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildInf() {
        if (rows > 0 && cols > 0) {
            return DMWrapper.inf(rows, cols);
        } else if (rows > 0) {
            return DMWrapper.inf(rows);
        } else if (sparsity != null) {
            return DMWrapper.inf(sparsity.getCasADiObject());
        } else {
            return DMWrapper.inf();
        }
    }

    /**
     * Builds a matrix or vector filled with random values.
     *
     * This method creates a matrix with the specified number of rows and columns,
     * or a vector if only the number of rows is specified. If no dimensions are set,
     * a 1x1 matrix filled with a random value is created. If sparsity is defined,
     * a matrix with the specified sparsity structure will be filled with random values.
     *
     * @return a DMWrapper containing the constructed matrix or vector filled with random values
     */
    public DMWrapper buildRandom() {
        if (rows > 0 && cols > 0) {
            return DMWrapper.rand(rows, cols);
        } else if (rows > 0) {
            return DMWrapper.rand(rows);
        } else if (sparsity != null) {
            return DMWrapper.rand(sparsity.getCasADiObject());
        } else {
            return DMWrapper.rand();
        }
    }

    /**
     * Builds a zero matrix DMWrapper with the specified dimensions.
     *
     * @return a DMWrapper containing the zero matrix
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildZeroMatrix() {
        if (rows > 0 && cols > 0) {
            return DMWrapper.zeros(rows, cols);
        } else if (rows > 0) {
            return DMWrapper.zeros(rows);
        } else if (sparsity != null) {
            return DMWrapper.zeros(sparsity.getCasADiObject());
        } else {
            return DMWrapper.zeros();
        }
    }

    /**
     * Builds an identity matrix DMWrapper with the specified size.
     *
     * @return a DMWrapper containing the identity matrix
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildIdentityMatrix() {
        if (identitySize == null) {
            throw new IllegalStateException("Identity size must be set.");
        }
        return DMWrapper.eye(identitySize);
    }

    /**
     * Builds a sparse matrix based on the specified sparsity structure.
     *
     * This method creates a sparse matrix using the defined sparsity structure.
     * If the sparsity is not set, an IllegalArgumentException is thrown.
     *
     * @return a DMWrapper containing the constructed sparse matrix
     * @throws IllegalArgumentException if the sparsity structure is not set
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper buildSparseMatrix() {
        if (sparsity == null) {
            throw new IllegalArgumentException("Sparsity must be set for identity matrix.");
        }
        return new DMWrapper(new DM(sparsity.getCasADiObject()));
    }

    /**
     * Builds a DMWrapper based on the current configuration of the builder.
     *
     * @return a DMWrapper containing the constructed DM object
     */
    @Override
    @SuppressWarnings("unchecked")
    public DMWrapper build() {
        if (sparsity != null && values != null) {
            return new DMWrapper(sparsity, buildFromValues());
        }
        if (sparsity != null) {
            return new DMWrapper(sparsity);
        }
        if (values != null) {
            return new DMWrapper(values);
        }
        if (rows > 0 && cols > 0) {
            return new DMWrapper(rows, cols);
        }
        if (rows > 0) {
            this.cols = 1L;
            return new DMWrapper(rows, cols);
        }
        if (cols > 0) {
            this.rows = 1L;
            return new DMWrapper(rows, cols);
        }
        if (rows > 0 && cols > 0 && rows == cols) {
            return DMWrapper.eye(rows);
        }
        return new DMWrapper();
    }

}