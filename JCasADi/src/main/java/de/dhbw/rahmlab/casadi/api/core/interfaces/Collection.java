package de.dhbw.rahmlab.casadi.api.core.interfaces;

import java.util.List;

/**
 * Interface representing a collection of vectors with various operations.
 * Provides methods to manipulate and query the collection.
 *
 * @param <T> The type of elements in the collection.
 */
public interface Collection<T> {

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
     * @param vector The element to set.
     * @return The previous element at the specified index.
     */
    T set(int index, T vector);

    /**
     * Inserts an element into the collection.
     *
     * @param vector The element to insert.
     * @param <N> The type of the collection.
     * @return The collection instance for method chaining.
     */
    <N extends Collection<?>> N insert(T vector);

    /**
     * Inserts an element at the specified index in the collection.
     *
     * @param index The index where the element should be inserted.
     * @param vector The element to insert.
     * @param <N> The type of the collection.
     * @return The collection instance for method chaining.
     */
    <N extends Collection<?>> N insert(int index, T vector);

    /**
     * Clears the collection and adds a new element.
     *
     * @param vector The element to add.
     * @param <N> The type of the collection.
     * @return The collection instance for method chaining.
     */
    <N extends Collection<?>> N clearAndAdd(T vector);

    /**
     * Reserves space and adds a new element to the collection.
     *
     * @param n The number of elements to reserve space for.
     * @param vector The element to add.
     * @param <N> The type of the collection.
     * @return The collection instance for method chaining.
     */
    <N extends Collection<?>> N reserveAndAdd(long n, T vector);

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
     * Returns the number of elements in the collection.
     *
     * @return The size of the collection.
     */
    int size();

    /**
     * Returns the capacity of the collection.
     *
     * @return The capacity of the collection.
     */
    long capacity();

    /**
     * Reserves space for the specified number of elements.
     *
     * @param n The number of elements to reserve space for.
     */
    void reserve(long n);

    /**
     * Checks if the collection is empty.
     *
     * @return True if the collection is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Clears all elements from the collection.
     */
    void clear();

    /**
     * Retrieves a list of all vectors in the collection.
     *
     * @return A list of vectors in the collection.
     */
    List<T> getVectors();

}
