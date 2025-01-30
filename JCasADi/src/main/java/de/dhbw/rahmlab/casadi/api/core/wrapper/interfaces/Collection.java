package de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces;

public interface Collection<T, N> {

    N getVector(int index);

    N setVector(int index, N vector);

    T add(N vector);

    T add(int index, N vector);

    N remove(int index);

    void removeRange(int fromIndex, int toIndex);

    int size();

    long capacity();

    void reserve(long n);

    boolean isEmpty();

    void clear();

}
