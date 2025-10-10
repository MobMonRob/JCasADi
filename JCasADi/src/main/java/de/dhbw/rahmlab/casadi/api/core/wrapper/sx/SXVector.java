package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorSX;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a vector of SXWrapper objects, backed by a StdVectorSX {@link StdVectorSX}.
 */
public class SXVector extends AbstractList<SXWrapper> implements Vector<SXWrapper> {

    private final StdVectorSX stdVectorSX;

    /**
     * Constructs an empty SXVector.
     * Initializes the internal StdVectorSX instance.
     */
    public SXVector() {
        this.stdVectorSX = new StdVectorSX();
    }

    /**
     * Constructs an SXVector with the specified initial SXWrapper elements.
     *
     * @param initialElements a variable number of SXWrapper objects to initialize the vector.
     */
    public SXVector(SXWrapper... initialElements) {
        this.stdVectorSX = new StdVectorSX();
        for (SXWrapper element : initialElements) {
            this.stdVectorSX.add(element.getCasADiObject());
        }
    }

    /**
     * Constructs an SXVector with the specified initial SXWrapper elements from an iterable collection.
     *
     * @param initialElements an iterable collection of SXWrapper objects to initialize the vector.
     */
    public SXVector(Iterable<SXWrapper> initialElements) {
        this.stdVectorSX = new StdVectorSX();
        initialElements.forEach(this::add);
    }

    /**
     * Constructs an SXVector with the specified initial numeric elements.
     *
     * @param initialElements a variable number of Number objects to initialize the vector.
     *                        Accepts instances of Number, including Double and Integer.
     */
    public SXVector(Number... initialElements) {
        this.stdVectorSX = new StdVectorSX();
        for (Number number : initialElements) {
            double value = number.doubleValue();
            this.stdVectorSX.add(new SX(value));
        }
    }

    /**
     * Constructs an SXVector from an existing StdVectorSX.
     *
     * @param stdVectorSX the StdVectorSX to use as the backing vector.
     */
    public SXVector(StdVectorSX stdVectorSX) {
        this.stdVectorSX = new StdVectorSX(stdVectorSX);
    }

    /**
     * Constructs a copy of the specified SXVector.
     *
     * @param sxVector the SXVector to copy.
     */
    public SXVector(SXVector sxVector) {
        this.stdVectorSX = new StdVectorSX(sxVector.getCasADiObject());
    }

    /**
     * Constructs an SXVector with a specified count of identical SXWrapper elements.
     *
     * @param count the number of elements to create.
     * @param sxWrapper the SXWrapper object to initialize each element.
     */
    public SXVector(int count, SXWrapper sxWrapper) {
        this.stdVectorSX = new StdVectorSX(count, sxWrapper.getCasADiObject());
    }

    /**
     * Retrieves the SXWrapper at the specified index.
     *
     * @param index the index of the element to retrieve.
     * @return the SXWrapper at the specified index.
     */
    @Override
    public SXWrapper get(int index) {
        return new SXWrapper(this.stdVectorSX.get(index));
    }

    /**
     * Sets the SXWrapper at the specified index to a new value.
     *
     * @param index the index of the element to set.
     * @param element the new SXWrapper to set.
     * @return the previous SXWrapper at the specified index.
     */
    @Override
    public SXWrapper set(int index, SXWrapper element) {
        return new SXWrapper(this.stdVectorSX.set(index, element.getCasADiObject()));
    }

    /**
     * Inserts a new SXWrapper element at the end of the vector.
     *
     * @param element the SXWrapper to add.
     * @return the current SXVector instance for method chaining.
     */
    @Override
    public SXVector insert(SXWrapper element) {
        this.stdVectorSX.add(element.getCasADiObject());
        return this;
    }

    /**
     * Inserts a new SXWrapper element at the specified index in the vector.
     *
     * @param index the index at which to insert the element.
     * @param element the SXWrapper to add.
     * @return the current SXVector instance for method chaining.
     */
    @Override
    public SXVector insert(int index, SXWrapper element) {
        this.stdVectorSX.add(index, element.getCasADiObject());
        return this;
    }

    /**
     * Clears the vector and adds a new SXWrapper element.
     *
     * @param element the SXWrapper to add after clearing.
     * @return the current SXVector instance for method chaining.
     */
    @Override
    public SXVector clearAndAdd(SXWrapper element) {
        this.clear();
        return this.insert(element);
    }

    /**
     * Reserves space for a specified number of elements and adds a new SXWrapper element.
     *
     * @param n the number of elements to reserve space for.
     * @param element the SXWrapper to add.
     * @return the current SXVector instance for method chaining.
     */
    @Override
    public SXVector reserveAndAdd(int n, SXWrapper element) {
        this.reserve(n);
        return this.insert(element);
    }

    /**
     * Removes the SXWrapper at the specified index.
     *
     * @param index the index of the element to remove.
     * @return the removed SXWrapper.
     */
    @Override
    public SXWrapper remove(int index) {
        return new SXWrapper(this.stdVectorSX.remove(index));
    }

    /**
     * Removes a range of elements from the vector, starting from the specified index
     * to the specified index (exclusive).
     *
     * @param fromIndex the starting index of the range to remove (inclusive).
     * @param toIndex the ending index of the range to remove (exclusive).
     * @throws IndexOutOfBoundsException if the specified indices are out of range.
     */
    @Override
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.remove(i);
        }
    }

    /**
     * Returns the number of elements in the vector.
     *
     * @return the size of the vector.
     */
    @Override
    public int size() {
        return this.stdVectorSX.size();
    }

    /**
     * Returns the capacity of the vector.
     *
     * @return the capacity of the vector.
     */
    @Override
    public long capacity() {
        return this.stdVectorSX.capacity();
    }

    /**
     * Reserves space for a specified number of elements in the vector.
     *
     * @param n the number of elements to reserve space for.
     */
    @Override
    public void reserve(int n) {
        this.stdVectorSX.reserve(n);
    }

    /**
     * Checks if the vector is empty.
     *
     * @return true if the vector is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.stdVectorSX.isEmpty();
    }

    /**
     * Clears all elements from the vector.
     */
    @Override
    public void clear() {
        this.stdVectorSX.clear();
    }

    /**
     * Returns the associated CasADi object.
     *
     * This method retrieves the instance of type {@link StdVectorSX}
     * that is stored in this class. The CasADi object
     * can be used for mathematical operations or further
     * processing.
     *
     * @return the CasADi object of type {@link StdVectorSX}
     */
    public StdVectorSX getCasADiObject() {
        return this.stdVectorSX;
    }

    /**
     * Returns the internal list of SXWrapper objects.
     *
     * @return the list of SXWrapper objects.
     */
    @Override
    public List<SXWrapper> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new SXWrapper element to the end of the vector.
     *
     * @param element the SXWrapper to add.
     * @return true if the element was successfully added.
     */
    public boolean add(SXWrapper element) {
        return this.stdVectorSX.add(element.getCasADiObject());
    }

    /**
     * Adds a new SXWrapper element at the specified index.
     *
     * @param index the index at which to add the element.
     * @param element the SXWrapper to add.
     */
    public void add(int index, SXWrapper element) {
        this.stdVectorSX.add(index, element.getCasADiObject());
    }

    /**
     * Returns a string representation of the SXVector.
     *
     * This method constructs a comma-separated list of the SXWrapper elements
     * contained in the vector, enclosed in square brackets. It overrides the
     * default toString method to provide a meaningful representation of the
     * SXVector's contents.
     *
     * @return a string representation of the SXVector
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.size(); i++) {
            sb.append(this.get(i).toString());
            if (i < this.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
