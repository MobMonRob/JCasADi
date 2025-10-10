package de.dhbw.rahmlab.casadi.api.core.wrapper.str;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorStdString;

/**
 * A vector of string values (StdVectorStdString) {@link StdVectorStdString}
 * with provides methods to manipulate and access its elements.
 */
public class StringVector {

    private final StdVectorStdString stdVectorStdString;

    /**
     * Constructs an empty StringVector.
     */
    public StringVector() {
        this.stdVectorStdString = new StdVectorStdString();
    }

    /**
     * Constructs a StringVector with the specified initial string values.
     *
     * @param initialElements an array of string values to initialize the vector
     */
    public StringVector(String[] initialElements) {
        this.stdVectorStdString = new StdVectorStdString(initialElements);
    }

    /**
     * Constructs a StringVector with the specified initial string values.
     *
     * @param initialElements an iterable collection of String values to initialize the vector
     */
    public StringVector(Iterable<String> initialElements) {
        this.stdVectorStdString = new StdVectorStdString(initialElements);
    }

    /**
     * Constructs a StringVector as a copy of another StringVector.
     *
     * @param other the StringVector to copy
     */
    public StringVector(StringVector other) {
        this.stdVectorStdString = new StdVectorStdString(other.getCasADiObject());
    }

    /**
     * Constructs a StringVector from an existing StdVectorStdString instance.
     *
     * @param stdVectorStdString the StdVectorStdString instance to initialize the vector with
     */
    public StringVector(StdVectorStdString stdVectorStdString) {
        this.stdVectorStdString = new StdVectorStdString(stdVectorStdString);
    }

    /**
     * Constructs a StringVector with a specified number of elements,
     * all initialized to the same string value.
     *
     * @param count the number of string elements to initialize in the vector
     * @param value the string value to initialize each element with
     */
    public StringVector(int count, String value) {
        this.stdVectorStdString = new StdVectorStdString(count, value);
    }

    /**
     * Retrieves the string value at the specified index.
     *
     * @param index the index of the string value to retrieve
     * @return the string value at the specified index
     */
    public String get(int index) {
        return this.stdVectorStdString.get(index);
    }

    /**
     * Sets the string value at the specified index and returns the previous value.
     *
     * @param index the index at which to set the string value
     * @param value the string value to set
     * @return the previous string value at the specified index
     */
    public String set(int index, String value) {
        return this.stdVectorStdString.set(index, value);
    }

    /**
     * Adds a string value to the end of the vector.
     *
     * @param value the string value to add
     * @return this vector for method chaining
     */
    public StringVector add(String value) {
        this.stdVectorStdString.add(value);
        return this;
    }

    /**
     * Adds a string value at the specified index.
     *
     * @param index the index at which to add the string value
     * @param value the string value to add
     */
    public StringVector add(int index, String value) {
        this.stdVectorStdString.add(index, value);
        return this;
    }

    /**
     * Removes the string value at the specified index and returns it.
     *
     * @param index the index of the string value to remove
     * @return the removed string value
     */
    public String remove(int index) {
        return this.stdVectorStdString.remove(index);
    }

    /**
     * Removes a range of string values from the vector.
     *
     * @param fromIndex the starting index of the range (inclusive)
     * @param toIndex the ending index of the range (exclusive)
     */
    public void removeRange(int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorStdString.remove(i);
        }
    }

    /**
     * Returns the number of string values in the vector.
     *
     * @return the size of the vector
     */
    public int size() {
        return this.stdVectorStdString.size();
    }

    /**
     * Returns the capacity of the underlying StdVectorStdString.
     *
     * @return the capacity of the vector
     */
    public long capacity() {
        return this.stdVectorStdString.capacity();
    }

    /**
     * Reserves space for the specified number of string values.
     *
     * @param n the number of string values to reserve space for
     */
    public void reserve(int n) {
        this.stdVectorStdString.reserve(n);
    }

    /**
     * Checks if the vector is empty.
     *
     * @return true if the vector is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.stdVectorStdString.isEmpty();
    }

    /**
     * Clears the Vector, removing all string values.
     */
    public void clear() {
        this.stdVectorStdString.clear();
    }

    /**
     * Returns the underlying StdVectorStdString object.
     *
     * @return the StdVectorStdString instance
     */
    public StdVectorStdString getCasADiObject() {
        return this.stdVectorStdString;
    }

}
