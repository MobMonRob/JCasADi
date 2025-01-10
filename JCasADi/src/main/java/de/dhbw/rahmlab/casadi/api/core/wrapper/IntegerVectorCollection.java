package de.dhbw.rahmlab.casadi.api.core.wrapper;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiInt;

/**
 * A collection that holds a vector of integer values (StdVectorCasadiInt) {@link StdVectorCasadiInt}
 * and provides methods to manipulate and access its elements.
 */
public class IntegerVectorCollection {

    private StdVectorCasadiInt stdVectorCasadiInt;

    /**
     * Constructs an empty IntegerVectorCollection.
     */
    public IntegerVectorCollection() {
        this.stdVectorCasadiInt = new StdVectorCasadiInt();
    }

    /**
     * Constructs an IntegerVectorCollection with the specified initial integer values.
     *
     * @param initialElements an array of long values to initialize the collection
     */
    public IntegerVectorCollection(long[] initialElements) {
        this.stdVectorCasadiInt = new StdVectorCasadiInt(initialElements);
    }

    /**
     * Constructs an IntegerVectorCollection with the specified initial integer values.
     *
     * @param initialElements an iterable collection of Long values to initialize the collection
     */
    public IntegerVectorCollection(Iterable<Long> initialElements) {
        this.stdVectorCasadiInt = new StdVectorCasadiInt(initialElements);
    }

    /**
     * Constructs an IntegerVectorCollection as a copy of another IntegerVectorCollection.
     *
     * @param other the IntegerVectorCollection to copy
     */
    public IntegerVectorCollection(IntegerVectorCollection other) {
        this.stdVectorCasadiInt = new StdVectorCasadiInt(other.getCasADiObject());
    }

    /**
     * Constructs an IntegerVectorCollection from an existing StdVectorCasadiInt instance.
     *
     * This constructor initializes the IntegerVectorCollection using the
     * provided StdVectorCasadiInt instance. The underlying data structure will
     * directly reference the given instance, allowing for manipulation
     * of the same data.
     *
     * @param stdVectorCasadiInt the StdVectorCasadiInt instance to initialize the collection with
     */
    public IntegerVectorCollection(StdVectorCasadiInt stdVectorCasadiInt) {
        this.stdVectorCasadiInt = stdVectorCasadiInt;
    }

    /**
     * Constructs an IntegerVectorCollection with a specified number of elements,
     * all initialized to the same long value.
     *
     * This constructor creates a new StdVectorCasadiInt with the specified count
     * of elements, each initialized to the provided long value.
     * This allows for the creation of a collection with a predefined size
     * and default values.
     *
     * @param count the number of integer elements to initialize in the collection
     * @param value the long value to initialize each element with
     */
    public IntegerVectorCollection(int count, long value) {
        this.stdVectorCasadiInt = new StdVectorCasadiInt(count, value);
    }

    /**
     * Retrieves the integer value at the specified index.
     *
     * @param index the index of the integer value to retrieve
     * @return the integer value at the specified index
     */
    public Long get(int index) {
        return this.stdVectorCasadiInt.get(index);
    }

    /**
     * Sets the integer value at the specified index and returns the previous value.
     *
     * @param index the index at which to set the integer value
     * @param value the long value to set
     * @return the previous integer value at the specified index
     */
    public Long set(int index, Long value) {
        return this.stdVectorCasadiInt.set(index, value);
    }

    /**
     * Adds an integer value to the end of the collection.
     *
     * @param value the long value to add
     * @return this IntegerVectorCollection for method chaining
     */
    public IntegerVectorCollection add(Long value) {
        this.stdVectorCasadiInt.add(value);
        return this;
    }

    /**
     * Adds an integer value at the specified index.
     *
     * @param index the index at which to add the integer value
     * @param value the long value to add
     * @return this IntegerVectorCollection for method chaining
     */
    public IntegerVectorCollection add(int index, Long value) {
        this.stdVectorCasadiInt.add(index, value);
        return this;
    }

    /**
     * Removes the integer value at the specified index and returns it.
     *
     * @param index the index of the integer value to remove
     * @return the removed integer value
     */
    public Long remove(int index) {
        return this.stdVectorCasadiInt.remove(index);
    }

    /**
     * Removes a range of integer values from the collection.
     *
     * @param fromIndex the starting index of the range (inclusive)
     * @param toIndex the ending index of the range (exclusive)
     */
    public void removeRange(int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorCasadiInt.remove(i);
        }
    }

    /**
     * Returns the number of integer values in the collection.
     *
     * @return the size of the collection
     */
    public int size() {
        return this.stdVectorCasadiInt.size();
    }

    /**
     * Returns the capacity of the underlying StdVectorCasadiInt.
     *
     * @return the capacity of the collection
     */
    public long capacity() {
        return this.stdVectorCasadiInt.capacity();
    }

    /**
     * Reserves space for the specified number of integer values.
     *
     * @param n the number of integer values to reserve space for
     */
    public void reserve(long n) {
        this.stdVectorCasadiInt.reserve(n);
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.stdVectorCasadiInt.isEmpty();
    }

    /**
     * Clears the collection, removing all integer values.
     */
    public void clear() {
        this.stdVectorCasadiInt.clear();
    }

    /**
     * Returns the underlying StdVectorCasadiInt object.
     *
     * @return the StdVectorCasadiInt instance
     */
    public StdVectorCasadiInt getCasADiObject() {
        return this.stdVectorCasadiInt;
    }
}