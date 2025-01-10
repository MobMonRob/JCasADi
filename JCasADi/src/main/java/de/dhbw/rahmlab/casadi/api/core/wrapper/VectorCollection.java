package de.dhbw.rahmlab.casadi.api.core.wrapper;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorMX;

/**
 * A collection that holds a vector of vectors (StdVectorVectorMX) {@link StdVectorVectorMX}
 * and provides methods to manipulate and access its elements.
 */
public class VectorCollection {

    private StdVectorVectorMX stdVectorVectorMX;

    /**
     * Constructs an empty VectorCollection.
     */
    public VectorCollection() {
        this.stdVectorVectorMX = new StdVectorVectorMX();
    }

    /**
     * Constructs a VectorCollection with the specified initial elements.
     *
     * @param initialElements an array of MXVector to initialize the collection
     */
    public VectorCollection(MXVector[] initialElements) {
        this.stdVectorVectorMX = new StdVectorVectorMX();
        for (MXVector vector : initialElements) {
            this.stdVectorVectorMX.add(vector.getCasADiObject());
        }
    }

    /**
     * Constructs a VectorCollection with the specified initial elements.
     *
     * @param initialElements an iterable collection of MXVector to initialize the collection
     */
    public VectorCollection(Iterable<MXVector> initialElements) {
        this.stdVectorVectorMX = new StdVectorVectorMX();
        initialElements.forEach(mxVector -> this.stdVectorVectorMX.add(mxVector.getCasADiObject()));
    }

    /**
     * Constructs a VectorCollection as a copy of another VectorCollection.
     *
     * @param other the VectorCollection to copy
     */
    public VectorCollection(VectorCollection other) {
        this.stdVectorVectorMX = new StdVectorVectorMX(other.getCasADiObject());
    }

    /**
     * Constructs a VectorCollection with a specified index and value.
     *
     * @param index the index at which to initialize the collection
     * @param value the MXVector to initialize at the specified index
     */
    public VectorCollection(int index, MXVector value) {
        this.stdVectorVectorMX = new StdVectorVectorMX(index, value.getCasADiObject());
    }

    /**
     * Constructs a VectorCollection from an existing StdVectorVectorMX instance.
     *
     * @param stdVectorVectorMX the StdVectorVectorMX instance to initialize the collection with
     */
    public VectorCollection(StdVectorVectorMX stdVectorVectorMX) {
        this.stdVectorVectorMX = stdVectorVectorMX;
    }

    /**
     * Retrieves the MXVector at the specified index.
     *
     * @param index the index of the MXVector to retrieve
     * @return the MXVector at the specified index
     */
    public MXVector getMXVector(int index) {
        return new MXVector(this.stdVectorVectorMX.get(index));
    }

    /**
     * Sets the MXVector at the specified index and returns the previous value.
     *
     * @param index the index at which to set the MXVector
     * @param vector the MXVector to set
     * @return the previous MXVector at the specified index
     */
    public MXVector setMXVector(int index, MXVector vector) {
        return new MXVector(this.stdVectorVectorMX.set(index, vector.getCasADiObject()));
    }

    /**
     * Adds an MXVector at the specified index.
     *
     * @param index the index at which to add the MXVector
     * @param vector the MXVector to add
     * @return this VectorCollection for method chaining
     */
    public VectorCollection add(int index, MXVector vector) {
        this.stdVectorVectorMX.add(index, vector.getCasADiObject());
        return this;
    }

    /**
     * Adds an MXVector to the end of the collection.
     *
     * @param vector the MXVector to add
     * @return this VectorCollection for method chaining
     */
    public VectorCollection add(MXVector vector) {
        this.stdVectorVectorMX.add(vector.getCasADiObject());
        return this;
    }

    /**
     * Removes the MXVector at the specified index and returns it.
     *
     * @param index the index of the MXVector to remove
     * @return the removed MXVector
     */
    public MXVector remove(int index) {
        return new MXVector(this.stdVectorVectorMX.remove(index));
    }

    /**
     * Removes a range of MXVectors from the collection.
     *
     * @param fromIndex the starting index of the range (inclusive)
     * @param toIndex the ending index of the range (exclusive)
     */
    public void removeRange(int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorVectorMX.remove(i);
        }
    }

    /**
     * Returns the number of MXVectors in the collection.
     *
     * @return the size of the collection
     */
    public int size() {
        return this.stdVectorVectorMX.size();
    }

    /**
     * Returns the capacity of the underlying StdVectorVectorMX.
     *
     * @return the capacity of the collection
     */
    public long capacity() {
        return this.stdVectorVectorMX.capacity();
    }

    /**
     * Reserves space for the specified number of MXVectors.
     *
     * @param n the number of MXVectors to reserve space for
     */
    public void reserve(long n) {
        this.stdVectorVectorMX.reserve(n);
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.stdVectorVectorMX.isEmpty();
    }

    /**
     * Clears the collection, removing all MXVectors.
     */
    public void clear() {
        this.stdVectorVectorMX.clear();
    }

    /**
     * Returns the underlying StdVectorVectorMX object.
     *
     * @return the StdVectorVectorMX instance
     */
    public StdVectorVectorMX getCasADiObject() {
        return this.stdVectorVectorMX;
    }

}
