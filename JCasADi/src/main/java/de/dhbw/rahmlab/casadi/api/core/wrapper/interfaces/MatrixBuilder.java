package de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces;

import de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity.SparsityWrapper;

public interface MatrixBuilder<T> {

    T setValue(double value);

    T setValues(Number... values);

    T setDimensions(long rows, long cols);

    T setRows(long rows);

    T setCols(long cols);

    T setIdentitySize(long identitySize);

    T setSparsity(SparsityWrapper sparsity);

    T setName(String name);

    <N extends Wrapper<?>> N buildFromValues();

    <N extends Wrapper<?>> N buildSymbolic();

    <N extends Wrapper<?>> N buildOnes();

    <N extends Wrapper<?>> N buildNaN();

    <N extends Wrapper<?>> N buildInf();

    <N extends Wrapper<?>> N buildZeroMatrix();

    <N extends Wrapper<?>> N buildIdentityMatrix();

    <N extends Wrapper<?>> N buildSparseMatrix();

    <N extends Wrapper<?>> N build();

}
