package de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces;

import java.util.List;

public interface Collection<T> {

    T get(int index);

    T set(int index, T vector);

    <N extends Collection<?>> N insert(T vector);

    <N extends Collection<?>> N insert(int index, T vector);

    <N extends Collection<?>> N clearAndAdd(T vector);

    <N extends Collection<?>> N reserveAndAdd(long n, T vector);

    T remove(int index);

    void removeRange(int fromIndex, int toIndex);

    int size();

    long capacity();

    void reserve(long n);

    boolean isEmpty();

    void clear();

    List<T> getVectors();

}
