package de.dhbw.rahmlab.casadi.api.core.interfaces;

import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;

/**
 * Interface for building matrix objects with various configurations.
 * Provides methods to set matrix properties and build different types of matrices.
 *
 * @param <T> The type of the builder, allowing for fluent interface usage.
 */
public interface MatrixBuilder<T> {

    /**
     * Sets a single value for the matrix.
     *
     * @param value The value to be set.
     * @return The builder instance for method chaining.
     */
    T setValue(double value);

    /**
     * Sets multiple values for the matrix.
     *
     * @param values The values to be set.
     * @return The builder instance for method chaining.
     */
    T setValues(Number... values);

    /**
     * Sets the dimensions of the matrix.
     *
     * @param rows The number of rows.
     * @param cols The number of columns.
     * @return The builder instance for method chaining.
     */
    T setDimensions(long rows, long cols);

    /**
     * Sets the number of rows for the matrix.
     *
     * @param rows The number of rows.
     * @return The builder instance for method chaining.
     */
    T setRows(long rows);

    /**
     * Sets the number of columns for the matrix.
     *
     * @param cols The number of columns.
     * @return The builder instance for method chaining.
     */
    T setCols(long cols);

    /**
     * Sets the size for an identity matrix.
     *
     * @param identitySize The size of the identity matrix.
     * @return The builder instance for method chaining.
     */
    T setIdentitySize(long identitySize);

    /**
     * Sets the sparsity pattern for the matrix.
     *
     * @param sparsity The sparsity pattern wrapper.
     * @return The builder instance for method chaining.
     */
    T setSparsity(SparsityWrapper sparsity);

    /**
     * Sets the name of the matrix.
     *
     * @param name The name to be set.
     * @return The builder instance for method chaining.
     */
    T setName(String name);

    /**
     * Builds a matrix from the set values.
     *
     * @param <N> The type of the matrix wrapper.
     * @return A matrix wrapper instance.
     */
    <N extends Wrapper<?>> N buildFromValues();

    /**
     * Builds a symbolic matrix.
     *
     * @param <N> The type of the matrix wrapper.
     * @return A symbolic matrix wrapper instance.
     */
    <N extends Wrapper<?>> N buildSymbolic();

    /**
     * Builds a matrix filled with ones.
     *
     * @param <N> The type of the matrix wrapper.
     * @return A matrix wrapper instance filled with ones.
     */
    <N extends Wrapper<?>> N buildOnes();

    /**
     * Builds a matrix filled with NaN values.
     *
     * @param <N> The type of the matrix wrapper.
     * @return A matrix wrapper instance filled with NaN values.
     */
    <N extends Wrapper<?>> N buildNaN();

    /**
     * Builds a matrix filled with infinity values.
     *
     * @param <N> The type of the matrix wrapper.
     * @return A matrix wrapper instance filled with infinity values.
     */
    <N extends Wrapper<?>> N buildInf();

    /**
     * Builds a zero matrix.
     *
     * @param <N> The type of the matrix wrapper.
     * @return A zero matrix wrapper instance.
     */
    <N extends Wrapper<?>> N buildZeroMatrix();

    /**
     * Builds an identity matrix.
     *
     * @param <N> The type of the matrix wrapper.
     * @return An identity matrix wrapper instance.
     */
    <N extends Wrapper<?>> N buildIdentityMatrix();

    /**
     * Builds a sparse matrix based on the set sparsity pattern.
     *
     * @param <N> The type of the matrix wrapper.
     * @return A sparse matrix wrapper instance.
     */
    <N extends Wrapper<?>> N buildSparseMatrix();

    /**
     * Builds a matrix with the current configuration.
     *
     * @param <N> The type of the matrix wrapper.
     * @return A matrix wrapper instance.
     */
    <N extends Wrapper<?>> N build();

}
