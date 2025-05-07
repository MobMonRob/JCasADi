package de.dhbw.rahmlab.casadi.api.core.interfaces;

import java.util.*;

public interface CasADiMap<T> {

    int size();

    boolean containsKey(Object key);

    T get(Object key);

    T put(String key, T value);

    T remove(Object key);

    Set<Map.Entry<String, T>> entrySet();

    boolean isEmpty();

    void clear();

}
