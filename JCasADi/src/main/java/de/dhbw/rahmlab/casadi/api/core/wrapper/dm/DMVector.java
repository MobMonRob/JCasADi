package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDM;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a vector of DMWrapper objects, backed by a StdVectorDM {@link StdVectorDM}.
 */
public class DMVector extends AbstractList<DMWrapper> implements Vector<DMWrapper> {

    private final StdVectorDM stdVectorDM;

    /**
     * Constructs an empty DMVector.
     * Initializes the internal StdVectorDM instance.
     */
    public DMVector() {
        this.stdVectorDM = new StdVectorDM();
    }

    /**
     * Constructs a DMVector from an existing StdVectorDM.
     *
     * @param stdVectorDM the StdVectorDM to use as the backing vector.
     */
    public DMVector(StdVectorDM stdVectorDM) {
        this.stdVectorDM = new StdVectorDM(stdVectorDM);
    }

    /**
     * Constructs a copy of the specified DMVector.
     *
     * @param dmVector the DMVector to copy.
     */
    public DMVector(DMVector dmVector) {
        this.stdVectorDM = new StdVectorDM(dmVector.getCasADiObject());
    }

    /**
     * Constructs a DMVector with the specified initial DMWrapper elements.
     *
     * @param initialElements a variable number of DMWrapper objects to initialize the vector.
     */
    public DMVector(DMWrapper... initialElements) {
        this.stdVectorDM = new StdVectorDM();
        for (DMWrapper dmWrapper : initialElements) {
            this.stdVectorDM.add(dmWrapper.getCasADiObject());
        }
    }

    /**
     * Constructs a DMVector with the specified initial DMWrapper elements from an iterable collection.
     *
     * @param initialElements an iterable collection of DMWrapper objects to initialize the vector.
     */
    public DMVector(Iterable<DMWrapper> initialElements) {
        this.stdVectorDM = new StdVectorDM();
        initialElements.forEach(this::add);
    }

    /**
     * Constructs a DMVector with the specified initial numeric elements.
     *
     * @param initialElements a variable number of Number objects to initialize the vector.
     *                        Accepts instances of Number, including Double and Integer.
     */
    public DMVector(Number... initialElements) {
        this.stdVectorDM = new StdVectorDM();
        for (Number number : initialElements) {
            double value = number.doubleValue();
            this.stdVectorDM.add(new DM(value));
        }
    }

    /**
     * Constructs a DMVector with a specified count of identical DMWrapper elements.
     *
     * @param count the number of elements to create.
     * @param dmWrapper the DMWrapper object to initialize each element.
     */
    public DMVector(int count, DMWrapper dmWrapper) {
        this.stdVectorDM = new StdVectorDM(count, dmWrapper.getCasADiObject());
    }

    /**
     * Retrieves the DMWrapper at the specified index.
     *
     * @param index the index of the element to retrieve.
     * @return the DMWrapper at the specified index.
     */
    @Override
    public DMWrapper get(int index) {
        return new DMWrapper(this.stdVectorDM.get(index));
    }

    /**
     * Sets the DMWrapper at the specified index to a new value.
     *
     * @param index the index of the element to set.
     * @param element the new DMWrapper to set.
     * @return the previous DMWrapper at the specified index.
     */
    @Override
    public DMWrapper set(int index, DMWrapper element) {
        return new DMWrapper(this.stdVectorDM.set(index, element.getCasADiObject()));
    }

    /**
     * Inserts a new DMWrapper element at the end of the vector.
     *
     * @param element the DMWrapper to add.
     * @return the current DMVector instance for method chaining.
     */
    @Override
    public DMVector insert(DMWrapper element) {
        this.stdVectorDM.add(element.getCasADiObject());
        return this;
    }

    /**
     * Inserts a new DMWrapper element at the specified index in the vector.
     *
     * @param index the index at which to insert the element.
     * @param element the DMWrapper to add.
     * @return the current DMVector instance for method chaining.
     */
    @Override
    public DMVector insert(int index, DMWrapper element) {
        this.stdVectorDM.add(index, element.getCasADiObject());
        return this;
    }

    /**
     * Clears the vector and adds a new DMWrapper element.
     *
     * @param element the DMWrapper to add after clearing.
     * @return the current DMVector instance for method chaining.
     */
    @Override
    public DMVector clearAndAdd(DMWrapper element) {
        this.clear();
        return this.insert(element);
    }

    /**
     * Reserves space for a specified number of elements and adds a new DMWrapper element.
     *
     * @param n the number of elements to reserve space for.
     * @param element the DMWrapper to add.
     * @return the current DMVector instance for method chaining.
     */
    @Override
    public DMVector reserveAndAdd(int n, DMWrapper element) {
        this.reserve(n);
        return this.insert(element);
    }

    /**
     * Removes the DMWrapper at the specified index.
     *
     * @param index the index of the element to remove.
     * @return the removed DMWrapper.
     */
    @Override
    public DMWrapper remove(int index) {
        return new DMWrapper(this.stdVectorDM.remove(index));
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
        return this.stdVectorDM.size();
    }

    /**
     * Returns the capacity of the vector.
     *
     * @return the capacity of the vector.
     */
    @Override
    public long capacity() {
        return this.stdVectorDM.capacity();
    }

    /**
     * Reserves space for a specified number of elements in the vector.
     *
     * @param n the number of elements to reserve space for.
     */
    @Override
    public void reserve(int n) {
        this.stdVectorDM.reserve(n);
    }

    /**
     * Checks if the vector is empty.
     *
     * @return true if the vector is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.stdVectorDM.isEmpty();
    }

    /**
     * Clears all elements from the vector.
     */
    @Override
    public void clear() {
        this.stdVectorDM.clear();
    }

    /**
     * Returns the associated CasADi object.
     *
     * This method retrieves the instance of type {@link StdVectorDM}
     * that is stored in this class. The CasADi object
     * can be used for mathematical operations or further
     * processing.
     *
     * @return the CasADi object of type {@link StdVectorDM}
     */
    public StdVectorDM getCasADiObject() {
        return this.stdVectorDM;
    }

    /**
     * Returns the internal list of DMWrapper objects.
     *
     * @return the list of DMWrapper objects.
     */
    @Override
    public List<DMWrapper> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new DMWrapper element to the end of the vector.
     *
     * @param element the DMWrapper to add.
     * @return true if the element was successfully added.
     */
    public boolean add(DMWrapper element) {
        return this.stdVectorDM.add(element.getCasADiObject());
    }

    /**
     * Adds a new DMWrapper element at the specified index.
     *
     * @param index the index at which to add the element.
     * @param element the DMWrapper to add.
     */
    public void add(int index, DMWrapper element) {
        this.stdVectorDM.add(index, element.getCasADiObject());
    }

    /**
     * Returns a string representation of the DMVector.
     *
     * This method constructs a comma-separated list of the DMWrapper elements
     * contained in the vector, enclosed in square brackets. It overrides the
     * default toString method to provide a meaningful representation of the
     * DMVector's contents.
     *
     * @return a string representation of the DMVector
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
