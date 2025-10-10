package de.dhbw.rahmlab.casadi.api.core.wrapper.mx;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorMX;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a vector of MXWrapper objects, backed by a StdVectorMX {@link StdVectorMX}.
 */
public class MXVector extends AbstractList<MXWrapper> implements Vector<MXWrapper> {

    private final StdVectorMX stdVectorMX;

    /**
     * Constructs an empty MXVector.
     * Initializes the internal StdVectorMX instance.
     */
    public MXVector() {
        this.stdVectorMX = new StdVectorMX();
    }

    /**
     * Constructs an MXVector with the specified initial elements.
     *
     * @param initialElements an iterable collection of MXWrapper objects to initialize the vector.
     */
    public MXVector(Iterable<MXWrapper> initialElements) {
        this.stdVectorMX = new StdVectorMX();
        initialElements.forEach(this::add);
    }

    /**
     * Constructs an MXVector with the specified initial elements.
     *
     * @param initialElements a variable number of MXWrapper objects to initialize the vector.
     */
    public MXVector(MXWrapper... initialElements) {
        this.stdVectorMX = new StdVectorMX();
        for (MXWrapper mxWrapper : initialElements) {
            this.stdVectorMX.add(mxWrapper.getCasADiObject());
        }
    }

    /**
     * Constructs an MXVector with the specified initial numeric elements.
     *
     * @param initialElements a variable number of Number objects to initialize the vector.
     *                        Accepts instances of Number, including Double and Integer.
     *                        Note: Conversion to double may involve rounding.
     */
    public MXVector(Number... initialElements) {
        this.stdVectorMX = new StdVectorMX();
        for (Number number : initialElements) {
            double value = number.doubleValue();
            this.stdVectorMX.add(new MX(value));
        }
    }

    /**
     * Constructs an MXVector from an existing StdVectorMX.
     *
     * @param stdVectorMX the StdVectorMX to use as the backing vector.
     */
    public MXVector(StdVectorMX stdVectorMX) {
        this.stdVectorMX = new StdVectorMX(stdVectorMX);
    }

    /**
     * Constructs a copy of the specified MXVector.
     *
     * @param mxVector the MXVector to copy.
     */
    public MXVector(MXVector mxVector) {
        this.stdVectorMX = new StdVectorMX(mxVector.getCasADiObject());
    }

    /**
     * Constructs an MXVector with a specified count of identical elements.
     *
     * @param count the number of elements to create.
     * @param mxWrapper the MXWrapper object to initialize each element.
     */
    public MXVector(int count, MXWrapper mxWrapper) {
        this.stdVectorMX = new StdVectorMX(count, mxWrapper.getCasADiObject());
    }

    /**
     * Retrieves the MXWrapper at the specified index.
     *
     * @param index the index of the element to retrieve.
     * @return the MXWrapper at the specified index.
     */
    @Override
    public MXWrapper get(int index) {
        return new MXWrapper(this.stdVectorMX.get(index));
    }

    /**
     * Sets the MXWrapper at the specified index to a new value.
     *
     * @param index the index of the element to set.
     * @param element the new MXWrapper to set.
     * @return the previous MXWrapper at the specified index.
     */
    @Override
    public MXWrapper set(int index, MXWrapper element) {
        return new MXWrapper(this.stdVectorMX.set(index, element.getCasADiObject()));
    }

    /**
     * Adds a new MXWrapper element to the end of the vector.
     *
     * @param element the MXWrapper to add.
     * @return the current MXVector instance for method chaining.
     */
    @Override
    public MXVector insert(MXWrapper element) {
        this.stdVectorMX.add(element.getCasADiObject());
        return this;
    }

    /**
     * Adds a new MXWrapper element at the specified index.
     *
     * @param index the index at which to add the element.
     * @param element the MXWrapper to add.
     * @return the current MXVector instance for method chaining.
     */
    @Override
    public MXVector insert(int index, MXWrapper element) {
        this.stdVectorMX.add(index, element.getCasADiObject());
        return this;
    }

    /**
     * Clears the vector and adds a new MXWrapper element.
     *
     * @param element the MXWrapper to add after clearing.
     * @return the current MXVector instance for method chaining.
     */
    @Override
    public MXVector clearAndAdd(MXWrapper element) {
        this.clear();
        return this.insert(element);
    }

    /**
     * Reserves space for a specified number of elements and adds a new MXWrapper element.
     *
     * @param n the number of elements to reserve space for.
     * @param element the MXWrapper to add.
     * @return the current MXVector instance for method chaining.
     */
    @Override
    public MXVector reserveAndAdd(int n, MXWrapper element) {
        this.reserve(n);
        return this.insert(element);
    }

    /**
     * Removes the MXWrapper at the specified index.
     *
     * @param index the index of the element to remove.
     * @return the removed MXWrapper.
     */
    @Override
    public MXWrapper remove(int index) {
        return new MXWrapper(this.stdVectorMX.remove(index));
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
            this.stdVectorMX.remove(i);
        }
    }

    /**
     * Returns the number of elements in the vector.
     *
     * @return the size of the vector.
     */
    @Override
    public int size() {
        return this.stdVectorMX.size();
    }

    /**
     * Returns the capacity of the vector.
     *
     * @return the capacity of the vector.
     */
    @Override
    public long capacity() {
        return this.stdVectorMX.capacity();
    }

    /**
     * Reserves space for a specified number of elements in the vector.
     *
     * @param n the number of elements to reserve space for.
     */
    @Override
    public void reserve(int n) {
        this.stdVectorMX.reserve(n);
    }

    /**
     * Checks if the vector is empty.
     *
     * @return true if the vector is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.stdVectorMX.isEmpty();
    }

    /**
     * Clears all elements from the vector.
     */
    @Override
    public void clear() {
        this.stdVectorMX.clear();
    }

    /**
     * Returns the associated CasADi object.
     *
     * This method retrieves the instance of type {@link StdVectorMX}
     * that is stored in this class. The CasADi object
     * can be used for mathematical operations or further
     * processing.
     *
     * @return the CasADi object of type {@link StdVectorMX}
     */
    public StdVectorMX getCasADiObject() {
        return this.stdVectorMX;
    }

    /**
     * Returns the internal list of MXWrapper objects.
     *
     * @return the list of MXWrapper objects.
     */
    @Override
    public List<MXWrapper> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new MXWrapper element to the end of the vector.
     *
     * This method overrides the add method from the AbstractList class.
     * It adds the specified MXWrapper element to the underlying StdVectorMX
     * and returns true if the operation is successful. Unlike the add method
     * in AbstractList, which returns a boolean indicating success, this method
     * specifically handles MXWrapper objects.
     *
     * @param element the MXWrapper to add to the vector
     * @return true if the element was successfully added
     */
    public boolean add(MXWrapper element) {
        return this.stdVectorMX.add(element.getCasADiObject());
    }

    /**
     * Inserts a new MXWrapper element at the specified index in the vector.
     *
     * This method overrides the add method from the AbstractList class.
     * It adds the specified MXWrapper element at the given index in the
     * underlying StdVectorMX. Unlike the add method in AbstractList, which
     * does not allow for returning a value, this method specifically deals
     * with MXWrapper objects and does not return any value.
     *
     * @param index the index at which to insert the MXWrapper element
     * @param element the MXWrapper to add to the vector
     */
    public void add(int index, MXWrapper element) {
        this.stdVectorMX.add(index, element.getCasADiObject());
    }

    /**
     * Returns a string representation of the MXVector.
     *
     * This method constructs a comma-separated list of the MXWrapper elements
     * contained in the vector, enclosed in square brackets. It overrides the
     * default toString method to provide a meaningful representation of the
     * MXVector's contents.
     *
     * @return a string representation of the MXVector
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
