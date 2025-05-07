package de.dhbw.rahmlab.casadi.api.core.wrapper.bool;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorBool;

/**
 * A collection that holds a vector of boolean values (StdVectorBool) {@link StdVectorBool}
 * and provides methods to manipulate and access its elements.
 */
public class BooleanVector {

    private final StdVectorBool stdVectorBool;

    /**
     * Constructs an empty BooleanVector.
     */
    public BooleanVector() {
        this.stdVectorBool = new StdVectorBool();
    }

    /**
     * Constructs a BooleanVector with the specified initial boolean values.
     *
     * @param initialElements an array of boolean values to initialize the collection
     */
    public BooleanVector(boolean[] initialElements) {
        this.stdVectorBool = new StdVectorBool(initialElements);
    }

    /**
     * Constructs a BooleanVector with the specified initial boolean values.
     *
     * @param initialElements an iterable collection of Boolean values to initialize the collection
     */
    public BooleanVector(Iterable<Boolean> initialElements) {
        this.stdVectorBool = new StdVectorBool();
        initialElements.forEach(this::add);
    }

    /**
     * Constructs a BooleanVector as a copy of another BooleanVector.
     *
     * @param other the BooleanVector to copy
     */
    public BooleanVector(BooleanVector other) {
        this.stdVectorBool = new StdVectorBool(other.getCasADiObject());
    }

    /**
     * Constructs a BooleanVector from an existing StdVectorBool instance.
     *
     * This constructor initializes the BooleanVector using the
     * provided StdVectorBool instance. The underlying data structure will
     * directly reference the given instance, allowing for manipulation
     * of the same data.
     *
     * @param stdVectorBool the StdVectorBool instance to initialize the collection with
     */
    public BooleanVector(StdVectorBool stdVectorBool) {
        this.stdVectorBool = stdVectorBool;
    }

    /**
     * Constructs a BooleanVector with a specified number of elements,
     * all initialized to the same boolean value.
     *
     * This constructor creates a new StdVectorBool with the specified count
     * of elements, each initialized to the provided boolean value.
     * This allows for the creation of a collection with a predefined size
     * and default values.
     *
     * @param count the number of boolean elements to initialize in the collection
     * @param value the boolean value to initialize each element with
     */
    public BooleanVector(int count, boolean value) {
        this.stdVectorBool = new StdVectorBool(count, value);
    }

    /**
     * Retrieves the boolean value at the specified index.
     *
     * @param index the index of the boolean value to retrieve
     * @return the boolean value at the specified index
     */
    public Boolean get(int index) {
        return this.stdVectorBool.get(index);
    }

    /**
     * Sets the boolean value at the specified index and returns the previous value.
     *
     * @param index the index at which to set the boolean value
     * @param value the boolean value to set
     * @return the previous boolean value at the specified index
     */
    public Boolean set(int index, Boolean value) {
        return this.stdVectorBool.set(index, value);
    }

    /**
     * Adds a boolean value to the end of the collection.
     *
     * @param value the boolean value to add
     * @return true if the value was added successfully
     */
    public BooleanVector add(Boolean value) {
        this.stdVectorBool.add(value);
        return this;
    }

    /**
     * Adds a boolean value at the specified index.
     *
     * @param index the index at which to add the boolean value
     * @param value the boolean value to add
     */
    public BooleanVector add(int index, Boolean value) {
        this.stdVectorBool.add(index, value);
        return this;
    }

    /**
     * Removes the boolean value at the specified index and returns it.
     *
     * @param index the index of the boolean value to remove
     * @return the removed boolean value
     */
    public Boolean remove(int index) {
        return this.stdVectorBool.remove(index);
    }

    /**
     * Removes a range of boolean values from the collection.
     *
     * @param fromIndex the starting index of the range (inclusive)
     * @param toIndex the ending index of the range (exclusive)
     */
    public void removeRange(int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorBool.remove(i);
        }
    }

    /**
     * Returns the number of boolean values in the collection.
     *
     * @return the size of the collection
     */
    public int size() {
        return this.stdVectorBool.size();
    }

    /**
     * Returns the capacity of the underlying StdVectorBool.
     *
     * @return the capacity of the collection
     */
    public long capacity() {
        return this.stdVectorBool.capacity();
    }

    /**
     * Reserves space for the specified number of boolean values.
     *
     * @param n the number of boolean values to reserve space for
     */
    public void reserve(long n) {
        this.stdVectorBool.reserve(n);
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.stdVectorBool.isEmpty();
    }

    /**
     * Clears the collection, removing all boolean values.
     */
    public void clear() {
        this.stdVectorBool.clear();
    }

    /**
     * Returns the underlying StdVectorBool object.
     *
     * @return the StdVectorBool instance
     */
    public StdVectorBool getCasADiObject() {
        return this.stdVectorBool;
    }
}