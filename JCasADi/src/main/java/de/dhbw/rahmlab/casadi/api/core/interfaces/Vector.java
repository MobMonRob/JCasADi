package de.dhbw.rahmlab.casadi.api.core.interfaces;

import java.util.List;

public interface Vector<T> {

    T get(int index);

    T set(int index, T element);

    <N extends Vector<?>> N insert(T element);

    <N extends Vector<?>> N insert(int index, T element);

    <N extends Vector<?>> N clearAndAdd(T element);

    <N extends Vector<?>> N reserveAndAdd(long n, T element);

    T remove(int index);

    void removeRange(int fromIndex, int toIndex);

    int size();

    long capacity();

    void reserve(long n);

    boolean isEmpty();

    void clear();

    List<T> getWrappers();

}
