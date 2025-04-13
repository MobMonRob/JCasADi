package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.im.IMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.MatrixBuilder;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Wrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;

import java.util.Arrays;


/**
 * Builder class for creating IMWrapper objects.
 *
 * This builder supports the creation of both numerical and symbolic IM objects,
 * covering the full functionality provided by the IMWrapper constructors.
 *
 * Using a fluent interface, IMBuilder allows the user to specify constant values,
 * arrays of values, dimensions, symbolic names, sparsity patterns, and identity sizes.
 */
public class IMBuilder implements MatrixBuilder<IMBuilder> {
    private Double value = null;
    private Long[] values = null;
    private Long rows = null;
    private Long cols = null;
    private String name = null;
    private SparsityWrapper sparsity = null;
    private Long identitySize = null;

    @Override
    public IMBuilder setValue(double value) {
        this.value = value;
        this.values = null;
        return this;
    }

    @Override
    public IMBuilder setValues(Number... values) {
        this.values = Arrays.stream(values)
                .map(Number::longValue)
                .toArray(Long[]::new);
        this.value = null;
        return this;
    }

    @Override
    public IMBuilder setDimensions(long rows, long cols) {
        this.rows = rows;
        this.cols = cols;
        return this;
    }

    @Override
    public IMBuilder setRows(long rows) {
        this.rows = rows;
        this.cols = 1L;
        return this;
    }

    @Override
    public IMBuilder setCols(long cols) {
        this.cols = cols;
        this.rows = 1L;
        return this;
    }

    @Override
    public IMBuilder setIdentitySize(long identitySize) {
        this.identitySize = identitySize;
        return this;
    }

    @Override
    public IMBuilder setSparsity(SparsityWrapper sparsity) {
        this.sparsity = sparsity;
        return this;
    }

    @Override
    public IMBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Builds an IMWrapper from numerical values.
     * This method is used when either a single constant or an array of values is set.
     *
     * @return an IMWrapper constructed from the specified value(s).
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper buildFromValues() {
        IMWrapper result;
        if (values != null && values.length > 0) {
            result = new IMWrapper(values);
        } else if (value != null) {
            result = new IMWrapper(value);
        } else {
            throw new IllegalStateException("Either a constant value or an array of values must be set.");
        }
        return result;
    }

    /**
     * Builds a symbolic IMWrapper.
     * If dimensions are provided, a matrix or vector is created.
     * If a sparsity structure is set, it is used to build a sparse symbolic IM.
     *
     * @return an IMWrapper representing a symbolic IM object.
     * @throws IllegalArgumentException if name is not set.
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper buildSymbolic() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must be set for symbolic IM.");
        }
        IMWrapper result;
        if (rows != null && cols != null && rows > 0 && cols > 0) {
            result = IMWrapper.sym(name, rows, cols);
        } else if (rows != null && rows > 0) {
            result = IMWrapper.sym(name, rows);
        } else if (sparsity != null) {
            result = IMWrapper.sym(name, sparsity.getCasADiObject());
        } else {
            result = IMWrapper.sym(name);
        }
        return result;
    }

    /**
     * Builds an IMWrapper filled with ones.
     *
     * @return an IMWrapper containing the ones matrix or vector.
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper buildOnes() {
        IMWrapper result;
        if (rows != null && cols != null) {
            result = IMWrapper.ones(rows, cols);
        } else if (rows != null) {
            result = IMWrapper.ones(rows);
        } else if (sparsity != null) {
            result = IMWrapper.ones(sparsity.getCasADiObject());
        } else {
            result = IMWrapper.ones();
        }
        return result;
    }

    /**
     * Builds an IMWrapper filled with NaN values.
     *
     * @return an IMWrapper containing the NaN matrix or vector.
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper buildNaN() {
        IMWrapper result;
        if (rows != null && cols != null) {
            result = IMWrapper.nan(rows, cols);
        } else if (rows != null) {
            result = IMWrapper.nan(rows);
        } else if (sparsity != null) {
            result = IMWrapper.nan(sparsity.getCasADiObject());
        } else {
            result = IMWrapper.nan();
        }
        return result;
    }

    /**
     * Builds an IMWrapper filled with Infinity values.
     *
     * @return an IMWrapper containing the Infinity matrix or vector.
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper buildInf() {
        IMWrapper result;
        if (rows != null && cols != null) {
            result = IMWrapper.inf(rows, cols);
        } else if (rows != null) {
            result = IMWrapper.inf(rows);
        } else if (sparsity != null) {
            result = IMWrapper.inf(sparsity.getCasADiObject());
        } else {
            result = IMWrapper.inf();
        }
        return result;
    }

    /**
     * Builds an IMWrapper representing a zero matrix or vector.
     *
     * @return an IMWrapper containing the zero matrix or vector.
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper buildZeroMatrix() {
        IMWrapper result;
        if (rows != null && cols != null) {
            result = IMWrapper.zeros(rows, cols);
        } else if (rows != null) {
            result = IMWrapper.zeros(rows);
        } else if (sparsity != null) {
            result = IMWrapper.zeros(sparsity.getCasADiObject());
        } else {
            result = IMWrapper.zeros();
        }
        return result;
    }

    /**
     * Builds an identity matrix IMWrapper.
     *
     * @return an IMWrapper containing an identity matrix.
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper buildIdentityMatrix() {
        if (identitySize == null) {
            throw new IllegalStateException("Identity size must be set.");
        }
        return IMWrapper.eye(identitySize);
    }

    /**
     * Builds a sparse IMWrapper based on the defined sparsity structure.
     *
     * @return an IMWrapper containing the constructed sparse matrix.
     * @throws IllegalArgumentException if sparsity is not set.
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper buildSparseMatrix() {
        if (sparsity == null) {
            throw new IllegalArgumentException("Sparsity must be set for sparse matrix creation.");
        }
        return new IMWrapper(sparsity);
    }

    /**
     * Builds an IMWrapper filled with random values.
     *
     * @return an IMWrapper containing the random matrix or vector.
     */
    public IMWrapper buildRandom() {
        IMWrapper result;
        if (rows != null && cols != null) {
            result = IMWrapper.rand(rows, cols);
        } else if (rows != null) {
            result = IMWrapper.rand(rows);
        } else if (sparsity != null) {
            result = IMWrapper.rand(sparsity.getCasADiObject());
        } else {
            result = IMWrapper.rand();
        }
        return result;
    }

    /**
     * Builds an IMWrapper based on the current builder configuration.
     * The build method selects the appropriate constructor based on:
     * <ul>
     *     <li>Numerical values (or a constant) if set</li>
     *     <li>Symbolic specification (if a name is provided)</li>
     *     <li>Otherwise, using dimensions or sparsity</li>
     * </ul>
     *
     * @return an IMWrapper representing the constructed IM object.
     */
    @Override
    @SuppressWarnings("unchecked")
    public IMWrapper build() {
        if (values != null || value != null) {
            // Numeric construction with optional sparsity information
            if (sparsity != null) {
                return new IMWrapper(sparsity, buildFromValues());
            }
            return buildFromValues();
        }
        if (name != null && !name.isEmpty()) {
            return buildSymbolic();
        }
        if (rows != null && cols != null) {
            return new IMWrapper(rows, cols);
        }
        if (rows != null) {
            this.cols = 1L;
            return new IMWrapper(rows, cols);
        }
        // Fallback: return an empty IMWrapper (interpreted as a scalar or empty IM)
        return new IMWrapper();
    }
}
