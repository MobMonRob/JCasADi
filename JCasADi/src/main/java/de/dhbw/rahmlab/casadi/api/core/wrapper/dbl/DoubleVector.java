package de.dhbw.rahmlab.casadi.api.core.wrapper.dbl;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

import java.util.AbstractList;
import java.util.Arrays;

/**
 * A collection that holds a vector of double values (StdVectorDouble) {@link StdVectorDouble}
 * and provides methods to manipulate and access its elements.
 */
public class DoubleVector extends AbstractList<Double> {

    private final StdVectorDouble stdVectorDouble;

    /**
     * Constructs an empty DoubleVector.
     */
    public DoubleVector() {
        this.stdVectorDouble = new StdVectorDouble();
    }

    /**
     * Constructs a DoubleVector with the specified initial double values.
     *
     * @param initialElements an array of double values to initialize the collection
     */
    public DoubleVector(double... initialElements) {
        this.stdVectorDouble = new StdVectorDouble(initialElements);
    }

    /**
     * Constructs a DoubleVector with the specified initial elements.
     *
     * @param initialElements the elements to initialize the vector with.
     *                        Each element is converted to a double value and added to the vector.
     *                        Accepts any number of elements of type Number.
     */
    public DoubleVector(Number... initialElements) {
        this.stdVectorDouble = new StdVectorDouble();
        Arrays.stream(initialElements).forEach(element -> {
            this.stdVectorDouble.add(element.doubleValue());
        });
    }

    /**
     * Constructs a DoubleVector with the specified initial double values.
     *
     * @param initialElements an iterable collection of Double values to initialize the collection
     */
    public DoubleVector(Iterable<Double> initialElements) {
        this.stdVectorDouble = new StdVectorDouble(initialElements);
    }

    /**
     * Constructs a DoubleVector as a copy of another DoubleVector.
     *
     * @param other the DoubleVector to copy
     */
    public DoubleVector(DoubleVector other) {
        this.stdVectorDouble = new StdVectorDouble(other.getCasADiObject());
    }

    /**
     * Constructs a DoubleVector from an existing StdVectorDouble instance.
     *
     * This constructor initializes the DoubleVector using the
     * provided StdVectorDouble instance. A new instance of StdVectorDouble
     * is created as a copy of the provided instance, allowing for independent
     * manipulation of the data.
     *
     * @param stdVectorDouble the StdVectorDouble instance to initialize the collection with
     */
    public DoubleVector(StdVectorDouble stdVectorDouble) {
        this.stdVectorDouble = new StdVectorDouble(stdVectorDouble);
    }

    /**
     * Constructs a DoubleVector with a specified number of elements,
     * all initialized to the same double value.
     *
     * This constructor creates a new StdVectorDouble with the specified count
     * of elements, each initialized to the provided double value.
     * This allows for the creation of a collection with a predefined size
     * and default values.
     *
     * @param count the number of double elements to initialize in the collection
     * @param value the double value to initialize each element with
     */
    public DoubleVector(int count, double value) {
        this.stdVectorDouble = new StdVectorDouble(count, value);
    }

    /**
     * Retrieves the double value at the specified index.
     *
     * @param index the index of the double value to retrieve
     * @return the double value at the specified index
     */
    public Double get(int index) {
        return this.stdVectorDouble.get(index);
    }

    /**
     * Sets the double value at the specified index and returns the previous value.
     *
     * @param index the index at which to set the double value
     * @param value the double value to set
     * @return the previous double value at the specified index
     */
    public Double set(int index, Double value) {
        return this.stdVectorDouble.set(index, value);
    }

    /**
     * Appends a double value to the end of the collection.
     *
     * This method adds the specified double value to the end of the
     * underlying StdVectorDouble and returns the current instance of
     * DoubleVector for method chaining.
     *
     * @param value the double value to be added to the collection
     * @return the current instance of DoubleVector for method chaining
     */
    public DoubleVector insert(Double value) {
        this.stdVectorDouble.add(value);
        return this;
    }

    /**
     * Inserts a double value at the specified index in the collection.
     *
     * This method adds the specified double value at the given index
     * in the underlying StdVectorDouble. If the index is out of bounds,
     * an IndexOutOfBoundsException will be thrown. The method returns
     * the current instance of DoubleVector for method chaining.
     *
     * @param index the index at which to insert the double value
     * @param value the double value to be added to the collection
     * @return the current instance of DoubleVector for method chaining
     */
    public DoubleVector insert(int index, Double value) {
        this.stdVectorDouble.add(index, value);
        return this;
    }

    /**
     * Removes the double value at the specified index and returns it.
     *
     * @param index the index of the double value to remove
     * @return the removed double value
     */
    public Double remove(int index) {
        return this.stdVectorDouble.remove(index);
    }

    /**
     * Removes a range of double values from the collection.
     *
     * @param fromIndex the starting index of the range (inclusive)
     * @param toIndex the ending index of the range (exclusive)
     */
    public void removeRange(int fromIndex, int toIndex) {
        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorDouble.remove(i);
        }
    }

    /**
     * Returns the number of double values in the collection.
     *
     * @return the size of the collection
     */
    public int size() {
        return this.stdVectorDouble.size();
    }

    /**
     * Returns the capacity of the underlying StdVectorDouble.
     *
     * @return the capacity of the collection
     */
    public long capacity() {
        return this.stdVectorDouble.capacity();
    }

    /**
     * Reserves space for the specified number of double values.
     *
     * @param n the number of double values to reserve space for
     */
    public void reserve(int n) {
        this.stdVectorDouble.reserve(n);
    }

    /**
     * Checks if the collection is empty.
     *
     * @return true if the collection is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.stdVectorDouble.isEmpty();
    }

    /**
     * Clears the collection, removing all double values.
     */
    public void clear() {
        this.stdVectorDouble.clear();
    }

    /**
     * Returns the underlying StdVectorDouble object.
     *
     * @return the StdVectorDouble instance
     */
    public StdVectorDouble getCasADiObject() {
        return this.stdVectorDouble;
    }
}
