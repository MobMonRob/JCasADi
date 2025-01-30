package de.dhbw.rahmlab.casadi.api.core.wrapper.std;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Collection;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDouble;

/**
 * A collection that holds a vector of DoubleVector values (StdVectorVectorDouble)
 * and provides methods to manipulate and access its elements.
 */
public class DoubleVectorCollection implements Collection<DoubleVectorCollection, DoubleVector> {

    private StdVectorVectorDouble stdVectorVectorDouble;

    /**
     * Constructs an empty DoubleVectorCollection.
     * Initializes an empty StdVectorVectorDouble to hold DoubleVector elements.
     */
    public DoubleVectorCollection() {
        this.stdVectorVectorDouble = new StdVectorVectorDouble();
    }

    /**
     * Constructs a DoubleVectorCollection with the specified initial DoubleVector values.
     *
     * @param initialElements an array of DoubleVector values to initialize the collection
     */
    public DoubleVectorCollection(DoubleVector[] initialElements) {
        this.stdVectorVectorDouble = new StdVectorVectorDouble();
        for(DoubleVector element : initialElements) {
            this.stdVectorVectorDouble.add(element.getCasADiObject());
        }
    }

    /**
     * Constructs a DoubleVectorCollection with the specified initial DoubleVector values.
     *
     * @param initialElements an iterable collection of DoubleVector values to initialize the collection
     */
    public DoubleVectorCollection(Iterable<DoubleVector> initialElements) {
        this.stdVectorVectorDouble = new StdVectorVectorDouble();
        initialElements.forEach(this::add);
    }

    /**
     * Constructs a DoubleVectorCollection from an existing StdVectorVectorDouble instance.
     *
     * @param stdVectorVectorDouble the StdVectorVectorDouble instance to initialize the collection with
     */
    public DoubleVectorCollection(StdVectorVectorDouble stdVectorVectorDouble) {
        this.stdVectorVectorDouble = new StdVectorVectorDouble(stdVectorVectorDouble);
    }

    /**
     * Constructs a DoubleVectorCollection as a copy of another DoubleVectorCollection.
     *
     * @param other the DoubleVectorCollection to copy
     */
    public DoubleVectorCollection(DoubleVectorCollection other) {
        this.stdVectorVectorDouble = new StdVectorVectorDouble(other.getCasADiObject());
    }

    /**
     * Constructs a DoubleVectorCollection with a specified number of elements,
     * all initialized to the same DoubleVector value.
     *
     * @param count the number of DoubleVector elements to initialize in the collection
     * @param value the DoubleVector value to initialize each element with
     */
    public DoubleVectorCollection(int count, DoubleVector value) {
        this.stdVectorVectorDouble = new StdVectorVectorDouble(count, value.getCasADiObject());
    }

    /**
     * Retrieves the DoubleVector value at the specified index.
     *
     * @param index the index of the DoubleVector value to retrieve
     * @return the DoubleVector value at the specified index
     */
    @Override
    public DoubleVector getVector(int index) {
        return new DoubleVector(this.stdVectorVectorDouble.get(index));
    }

    /**
     * Sets the DoubleVector value at the specified index and returns the previous value.
     *
     * @param index the index at which to set the DoubleVector value
     * @param value the DoubleVector value to set
     * @return the previous DoubleVector value at the specified index
     */
    @Override
    public DoubleVector setVector(int index, DoubleVector value) {
        return new DoubleVector(this.stdVectorVectorDouble.set(index, value.getCasADiObject()));
    }

    /**
     * Appends a DoubleVector value to the end of the collection.
     *
     * @param value the DoubleVector value to be added to the collection
     * @return the current instance of DoubleVectorCollection for method chaining
     */
    @Override
    public DoubleVectorCollection add(DoubleVector value) {
        this.stdVectorVectorDouble.add(value.getCasADiObject());
        return this;
    }

    /**
     * Inserts a DoubleVector value at the specified index in the collection.
     *
     * @param index the index at which to insert the DoubleVector value
     * @param value the DoubleVector value to be added to the collection
     * @return the current instance of DoubleVectorCollection for method chaining
     */
    @Override
    public DoubleVectorCollection add(int index, DoubleVector value) {
        this.stdVectorVectorDouble.add(index, value.getCasADiObject());
        return this;
    }

    /**
     * Removes the DoubleVector value at the specified index and returns it.
     *
     * @param index the index of the DoubleVector value to remove
     * @return the removed DoubleVector value
     */
    @Override
    public DoubleVector remove(int index) {
        return new DoubleVector(this.stdVectorVectorDouble.remove(index));
    }

    /**
     * Removes a range of DoubleVector values from the collection.
     *
     * @param fromIndex the starting index of the range (inclusive)
     * @param toIndex the ending index of the range (exclusive)
     */
    @Override
    public void removeRange(int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorVectorDouble.remove(i);
        }
    }

    /**
     * Returns the number of DoubleVector values in the collection.
     *
     * @return the size of the collection
     */
    @Override
    public int size() {
        return this.stdVectorVectorDouble.size();
    }

    /**
     * Returns the capacity of the underlying StdVectorVectorDouble.
     *
     * @return the capacity of the collection
     */
    @Override
    public long capacity() {
        return this.stdVectorVectorDouble.capacity();
    }

    /**
     * Reserves space for the specified number of DoubleVector values.
     *
     * @param n the number of DoubleVector values to reserve space for
     */
    @Override
    public void reserve(long n) {
        this.stdVectorVectorDouble.reserve(n);
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.stdVectorVectorDouble.isEmpty();
    }

    /**
     * Clears the collection, removing all DoubleVector values.
     */
    @Override
    public void clear() {
        this.stdVectorVectorDouble.clear();
    }

    /**
     * Returns the underlying StdVectorVectorDouble object.
     *
     * @return the StdVectorVectorDouble instance
     */
    public StdVectorVectorDouble getCasADiObject() {
        return this.stdVectorVectorDouble;
    }

}
