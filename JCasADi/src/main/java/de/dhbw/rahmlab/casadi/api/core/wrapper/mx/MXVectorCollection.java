package de.dhbw.rahmlab.casadi.api.core.wrapper.mx;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Collection;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorMX;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A collection that holds a vector of vectors (StdVectorVectorMX) {@link StdVectorVectorMX}
 * and provides methods to manipulate and access its elements.
 */
public class MXVectorCollection extends AbstractList<MXVector> implements Collection<MXVector> {

    private final StdVectorVectorMX stdVectorVectorMX;

    /**
     * Constructs an empty VectorCollection.
     */
    public MXVectorCollection() {
        this.stdVectorVectorMX = new StdVectorVectorMX();
    }

    /**
     * Constructs a VectorCollection with the specified initial elements.
     *
     * @param initialElements an array of MXVector to initialize the collection
     */
    public MXVectorCollection(MXVector... initialElements) {
        this.stdVectorVectorMX = new StdVectorVectorMX();
        Arrays.stream(initialElements).forEach(
                element -> this.stdVectorVectorMX.add(element.getCasADiObject())
        );
    }

    /**
     * Constructs a VectorCollection with the specified initial elements.
     *
     * @param initialElements an iterable collection of MXVector to initialize the collection
     */
    public MXVectorCollection(Iterable<MXVector> initialElements) {
        this.stdVectorVectorMX = new StdVectorVectorMX();
        initialElements.forEach(
                element -> this.stdVectorVectorMX.add(element.getCasADiObject())
        );
    }

    /**
     * Constructs a VectorCollection as a copy of another VectorCollection.
     *
     * @param other the VectorCollection to copy
     */
    public MXVectorCollection(MXVectorCollection other) {
        this.stdVectorVectorMX = new StdVectorVectorMX(other.getCasADiObject());
    }

    /**
     * Constructs a VectorCollection with a specified index and value.
     *
     * @param index the index at which to initialize the collection
     * @param value the MXVector to initialize at the specified index
     */
    public MXVectorCollection(int index, MXVector value) {
        this.stdVectorVectorMX = new StdVectorVectorMX(index, value.getCasADiObject());
    }

    /**
     * Constructs a VectorCollection from an existing StdVectorVectorMX instance.
     *
     * @param stdVectorVectorMX the StdVectorVectorMX instance to initialize the collection with
     */
    public MXVectorCollection(StdVectorVectorMX stdVectorVectorMX) {
        this.stdVectorVectorMX = new StdVectorVectorMX(stdVectorVectorMX);
    }

    /**
     * Retrieves the MXVector at the specified index.
     *
     * @param index the index of the MXVector to retrieve
     * @return the MXVector at the specified index
     */
    @Override
    public MXVector get(int index) {
        return new MXVector(this.stdVectorVectorMX.get(index));
    }

    /**
     * Sets the MXVector at the specified index and returns the previous value.
     *
     * @param index the index at which to set the MXVector
     * @param vector the MXVector to set
     * @return the previous MXVector at the specified index
     */
    @Override
    public MXVector set(int index, MXVector vector) {
        return new MXVector(this.stdVectorVectorMX.set(index, vector.getCasADiObject()));
    }

    /**
     * Adds an MXVector at the specified index.
     *
     * @param index the index at which to add the MXVector
     * @param vector the MXVector to add
     * @return this VectorCollection for method chaining
     */
    @Override
    public MXVectorCollection insert(int index, MXVector vector) {
        this.stdVectorVectorMX.add(index, vector.getCasADiObject());
        return this;
    }

    /**
     * Adds an MXVector to the end of the collection.
     *
     * @param vector the MXVector to add
     * @return this VectorCollection for method chaining
     */
    @Override
    public MXVectorCollection insert(MXVector vector) {
        this.stdVectorVectorMX.add(vector.getCasADiObject());
        return this;
    }

    @Override
    public MXVectorCollection clearAndAdd(MXVector vector) {
        this.clear();
        return this.insert(vector);
    }

    @Override
    public MXVectorCollection reserveAndAdd(int n, MXVector vector) {
        this.reserve(n);
        return this.insert(vector);
    }

    /**
     * Removes the MXVector at the specified index and returns it.
     *
     * @param index the index of the MXVector to remove
     * @return the removed MXVector
     */
    @Override
    public MXVector remove(int index) {
        return new MXVector(this.stdVectorVectorMX.remove(index));
    }

    /**
     * Removes a range of MXVectors from the collection.
     *
     * @param fromIndex the starting index of the range (inclusive)
     * @param toIndex the ending index of the range (exclusive)
     */
    @Override
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorVectorMX.remove(i);
        }
    }

    /**
     * Returns the number of MXVectors in the collection.
     *
     * @return the size of the collection
     */
    @Override
    public int size() {
        return this.stdVectorVectorMX.size();
    }

    /**
     * Returns the capacity of the underlying StdVectorVectorMX.
     *
     * @return the capacity of the collection
     */
    @Override
    public long capacity() {
        return this.stdVectorVectorMX.capacity();
    }

    /**
     * Reserves space for the specified number of MXVectors.
     *
     * @param n the number of MXVectors to reserve space for
     */
    @Override
    public void reserve(int n) {
        this.stdVectorVectorMX.reserve(n);
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.stdVectorVectorMX.isEmpty();
    }

    /**
     * Clears the collection, removing all MXVectors.
     */
    @Override
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

    @Override
    public List<MXVector> getVectors() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(MXVector element) {
        return this.stdVectorVectorMX.add(element.getCasADiObject());
    }

    public void add(int index, MXVector element) {
        this.stdVectorVectorMX.add(index, element.getCasADiObject());
    }

}
