package de.dhbw.rahmlab.casadi.api.core.builder;

import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;

/**
 * Builder class for creating SparsityWrapper objects.
 * <p>
 * This builder provides a fluent API to configure different types of sparsity patterns:
 * dense, unit, upper, lower, diag, band, and banded. Depending on the chosen type,
 * the builder accepts the required parameters (e.g., dimensions, band width, etc.) and
 * constructs the appropriate SparsityWrapper via the corresponding factory method.
 */
public class SparsityBuilder {

    // Supported sparsity types
    public enum Type {
        DENSE, UNIT, UPPER, LOWER, DIAG, BAND, BANDED, CUSTOM
    }

    private Type type = null;
    private Long nrow = null;
    private Long ncol = null;
    private Long unitElement = null; // for UNIT: which element is nonzero
    private Long bandWidth = null;   // for BAND or BANDED: the parameter p
    private Boolean orderRows = null; // Option for custom constructor (optional)

    // Custom type can be set directly via a SparsityWrapper instance (optional)
    private SparsityWrapper customSparsity = null;

    /**
     * Selects the dense sparsity pattern.
     *
     * @param nrow the number of rows.
     * @param ncol the number of columns.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder dense(long nrow, long ncol) {
        this.type = Type.DENSE;
        this.nrow = nrow;
        this.ncol = ncol;
        return this;
    }

    /**
     * Selects the unit sparsity pattern.
     *
     * @param n the number of rows (and columns if square).
     * @param el the index (or element) that is nonzero.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder unit(long n, long el) {
        this.type = Type.UNIT;
        this.nrow = n;
        this.ncol = n; // unit pattern is typically square
        this.unitElement = el;
        return this;
    }

    /**
     * Selects the upper triangular sparsity pattern.
     *
     * @param n the dimension of the square matrix.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder upper(long n) {
        this.type = Type.UPPER;
        this.nrow = n;
        this.ncol = n;
        return this;
    }

    /**
     * Selects the lower triangular sparsity pattern.
     *
     * @param n the dimension of the square matrix.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder lower(long n) {
        this.type = Type.LOWER;
        this.nrow = n;
        this.ncol = n;
        return this;
    }

    /**
     * Selects the diagonal sparsity pattern.
     *
     * @param nrow the number of rows.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder diag(long nrow) {
        this.type = Type.DIAG;
        this.nrow = nrow;
        this.ncol = nrow;
        return this;
    }

    /**
     * Selects the diagonal sparsity pattern with different row and column counts.
     *
     * @param nrow the number of rows.
     * @param ncol the number of columns.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder diag(long nrow, long ncol) {
        this.type = Type.DIAG;
        this.nrow = nrow;
        this.ncol = ncol;
        return this;
    }

    /**
     * Selects the band sparsity pattern.
     *
     * @param n the dimension of the square matrix.
     * @param p the band parameter.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder band(long n, long p) {
        this.type = Type.BAND;
        this.nrow = n;
        this.ncol = n;
        this.bandWidth = p;
        return this;
    }

    /**
     * Selects the banded sparsity pattern.
     *
     * @param n the dimension of the square matrix.
     * @param p the band parameter.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder banded(long n, long p) {
        this.type = Type.BANDED;
        this.nrow = n;
        this.ncol = n;
        this.bandWidth = p;
        return this;
    }

    /**
     * Sets a custom sparsity wrapper instance.
     *
     * @param customSparsity the custom SparsityWrapper.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder custom(SparsityWrapper customSparsity) {
        this.type = Type.CUSTOM;
        this.customSparsity = customSparsity;
        return this;
    }

    /**
     * (Optional) Sets if the rows should be ordered.
     *
     * @param orderRows true if rows should be ordered, false otherwise.
     * @return the current instance of SparsityBuilder.
     */
    public SparsityBuilder setOrderRows(boolean orderRows) {
        this.orderRows = orderRows;
        return this;
    }

    /**
     * Builds the SparsityWrapper based on the current builder configuration.
     *
     * @return a SparsityWrapper instance representing the configured sparsity pattern.
     * @throws IllegalStateException if required parameters for the chosen type are missing.
     */
    public SparsityWrapper build() {
        if (this.type == null) {
            throw new IllegalStateException("Sparsity type must be selected.");
        }
        switch (this.type) {
            case DENSE:
                if (nrow == null)
                    throw new IllegalStateException("nrow must be set for dense sparsity.");
                if (ncol == null)
                    throw new IllegalStateException("ncol must be set for dense sparsity.");
                return SparsityWrapper.dense(nrow, ncol);

            case UNIT:
                if (nrow == null)
                    throw new IllegalStateException("n must be set for unit sparsity.");
                if (unitElement == null)
                    throw new IllegalStateException("unit element must be set for unit sparsity.");
                return SparsityWrapper.unit(nrow, unitElement);

            case UPPER:
                if (nrow == null)
                    throw new IllegalStateException("n must be set for upper sparsity.");
                return SparsityWrapper.upper(nrow);

            case LOWER:
                if (nrow == null)
                    throw new IllegalStateException("n must be set for lower sparsity.");
                return SparsityWrapper.lower(nrow);

            case DIAG:
                if (nrow == null)
                    throw new IllegalStateException("nrow must be set for diag sparsity.");
                if (ncol == null) {
                    return SparsityWrapper.diag(nrow);
                } else {
                    return SparsityWrapper.diag(nrow, ncol);
                }

            case BAND:
                if (nrow == null)
                    throw new IllegalStateException("n must be set for band sparsity.");
                if (bandWidth == null)
                    throw new IllegalStateException("band width must be set for band sparsity.");
                return SparsityWrapper.band(nrow, bandWidth);

            case BANDED:
                if (nrow == null)
                    throw new IllegalStateException("n must be set for banded sparsity.");
                if (bandWidth == null)
                    throw new IllegalStateException("band width must be set for banded sparsity.");
                return SparsityWrapper.banded(nrow, bandWidth);

            case CUSTOM:
                if (customSparsity == null)
                    throw new IllegalStateException("Custom sparsity must be provided.");
                return customSparsity;

            default:
                throw new IllegalStateException("Unsupported sparsity type.");
        }
    }
}
