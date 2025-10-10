package de.dhbw.rahmlab.casadi.api.core.interfaces;

import java.util.List;

/**
 * Interface representing a vector structure with various operations.
 * Provides methods to manipulate and query elements within the vector.
 *
 * @param <T> The type of elements stored in the vector.
 */
public interface Vector<T> {

    /**
     * Retrieves the element at the specified index.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index.
     */
    T get(int index);

    /**
     * Sets the element at the specified index.
     *
     * @param index The index where the element should be set.
     * @param element The element to set.
     * @return The previous element at the specified index.
     */
    T set(int index, T element);

    /**
     * Inserts an element into the vector.
     *
     * @param element The element to insert.
     * @param <N> The type of the vector.
     * @return The vector instance for method chaining.
     */
    <N extends Vector<?>> N insert(T element);

    /**
     * Inserts an element at the specified index in the vector.
     *
     * @param index The index where the element should be inserted.
     * @param element The element to insert.
     * @param <N> The type of the vector.
     * @return The vector instance for method chaining.
     */
    <N extends Vector<?>> N insert(int index, T element);

    /**
     * Clears the vector and adds a new element.
     *
     * @param element The element to add.
     * @param <N> The type of the vector.
     * @return The vector instance for method chaining.
     */
    <N extends Vector<?>> N clearAndAdd(T element);

    /**
     * Reserves space and adds a new element to the vector.
     *
     * @param n The number of elements to reserve space for.
     * @param element The element to add.
     * @param <N> The type of the vector.
     * @return The vector instance for method chaining.
     */
    <N extends Vector<?>> N reserveAndAdd(int n, T element);

    /**
     * Removes the element at the specified index.
     *
     * @param index The index of the element to remove.
     * @return The removed element.
     */
    T remove(int index);

    /**
     * Removes elements in the specified range.
     *
     * @param fromIndex The starting index of the range.
     * @param toIndex The ending index of the range.
     */
    void removeRange(int fromIndex, int toIndex);

    /**
     * Returns the number of elements in the vector.
     *
     * @return The size of the vector.
     */
    int size();

    /**
     * Returns the capacity of the vector.
     *
     * @return The capacity of the vector.
     */
    long capacity();

    /**
     * Reserves space for the specified number of elements.
     *
     * @param n The number of elements to reserve space for.
     */
    void reserve(int n);

    /**
     * Checks if the vector is empty.
     *
     * @return True if the vector contains no elements, false otherwise.
     */
    boolean isEmpty();

    /**
     * Clears all elements from the vector.
     */
    void clear();

    /**
     * Retrieves a list of all wrappers in the vector.
     *
     * @return A list of wrappers in the vector.
     */
    List<T> getWrappers();

}
