package de.dhbw.rahmlab.casadi.api.core.wrapper;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorStdString;

/**
 * A collection that holds a vector of string values (StdVectorStdString) {@link StdVectorStdString}
 * and provides methods to manipulate and access its elements.
 */
public class StringVectorCollection {

    private StdVectorStdString stdVectorStdString;

    /**
     * Constructs an empty StringVectorCollection.
     */
    public StringVectorCollection() {
        this.stdVectorStdString = new StdVectorStdString();
    }

    /**
     * Constructs a StringVectorCollection with the specified initial string values.
     *
     * @param initialElements an array of string values to initialize the collection
     */
    public StringVectorCollection(String[] initialElements) {
        this.stdVectorStdString = new StdVectorStdString(initialElements);
    }

    /**
     * Constructs a StringVectorCollection with the specified initial string values.
     *
     * @param initialElements an iterable collection of String values to initialize the collection
     */
    public StringVectorCollection(Iterable<String> initialElements) {
        this.stdVectorStdString = new StdVectorStdString(initialElements);
    }

    /**
     * Constructs a StringVectorCollection as a copy of another StringVectorCollection.
     *
     * @param other the StringVectorCollection to copy
     */
    public StringVectorCollection(StringVectorCollection other) {
        this.stdVectorStdString = new StdVectorStdString(other.getCasADiObject());
    }

    /**
     * Constructs a StringVectorCollection from an existing StdVectorStdString instance.
     *
     * @param stdVectorStdString the StdVectorStdString instance to initialize the collection with
     */
    public StringVectorCollection(StdVectorStdString stdVectorStdString) {
        this.stdVectorStdString = new StdVectorStdString(stdVectorStdString);
    }

    /**
     * Constructs a StringVectorCollection with a specified number of elements,
     * all initialized to the same string value.
     *
     * @param count the number of string elements to initialize in the collection
     * @param value the string value to initialize each element with
     */
    public StringVectorCollection(int count, String value) {
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
     * Adds a string value to the end of the collection.
     *
     * @param value the string value to add
     * @return this collection for method chaining
     */
    public StringVectorCollection add(String value) {
        this.stdVectorStdString.add(value);
        return this;
    }

    /**
     * Adds a string value at the specified index.
     *
     * @param index the index at which to add the string value
     * @param value the string value to add
     */
    public StringVectorCollection add(int index, String value) {
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
     * Removes a range of string values from the collection.
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
     * Returns the number of string values in the collection.
     *
     * @return the size of the collection
     */
    public int size() {
        return this.stdVectorStdString.size();
    }

    /**
     * Returns the capacity of the underlying StdVectorStdString.
     *
     * @return the capacity of the collection
     */
    public long capacity() {
        return this.stdVectorStdString.capacity();
    }

    /**
     * Reserves space for the specified number of string values.
     *
     * @param n the number of string values to reserve space for
     */
    public void reserve(long n) {
        this.stdVectorStdString.reserve(n);
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.stdVectorStdString.isEmpty();
    }

    /**
     * Clears the collection, removing all string values.
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
