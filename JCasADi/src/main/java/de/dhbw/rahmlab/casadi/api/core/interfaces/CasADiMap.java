package de.dhbw.rahmlab.casadi.api.core.interfaces;

import java.util.*;

/**
 * Interface representing a map structure for CasADi objects.
 * Provides methods to manipulate and query key-value pairs.
 *
 * @param <T> The type of values stored in the map.
 */
public interface CasADiMap<T> {

    /**
     * Returns the number of key-value pairs in the map.
     *
     * @return The size of the map.
     */
    int size();

    /**
     * Checks if the map contains the specified key.
     *
     * @param key The key to check for presence in the map.
     * @return True if the key is present, false otherwise.
     */
    boolean containsKey(Object key);

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the specified key, or null if the key is not present.
     */
    T get(Object key);

    /**
     * Associates the specified value with the specified key in the map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with the key, or null if there was no mapping.
     */
    T put(String key, T value);

    /**
     * Removes the mapping for the specified key from the map if present.
     *
     * @param key The key whose mapping is to be removed.
     * @return The previous value associated with the key, or null if there was no mapping.
     */
    T remove(Object key);

    /**
     * Returns a set view of the mappings contained in the map.
     *
     * @return A set of map entries representing the key-value pairs in the map.
     */
    Set<Map.Entry<String, T>> entrySet();

    /**
     * Checks if the map is empty.
     *
     * @return True if the map contains no key-value pairs, false otherwise.
     */
    boolean isEmpty();

    /**
     * Removes all mappings from the map.
     */
    void clear();

}
