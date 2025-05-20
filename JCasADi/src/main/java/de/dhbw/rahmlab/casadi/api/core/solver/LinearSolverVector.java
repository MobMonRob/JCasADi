package de.dhbw.rahmlab.casadi.api.core.solver;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiLinsol;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents a vector of LinearSolver objects, providing methods to manipulate and access a collection of solvers.
 * This class extends AbstractList and implements the Vector interface, allowing for flexible operations on the solvers.
 */
public class LinearSolverVector extends AbstractList<LinearSolver> implements Vector<LinearSolver> {

    private final StdVectorCasadiLinsol stdVectorCasadiLinsol;

    /**
     * Constructs an empty LinearSolverVector.
     */
    public LinearSolverVector() {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol();
    }

    /**
     * Constructs a LinearSolverVector using an existing StdVectorCasadiLinsol object.
     *
     * @param stdVectorCasadiLinsol the StdVectorCasadiLinsol object used to initialize the vector.
     */
    public LinearSolverVector(StdVectorCasadiLinsol stdVectorCasadiLinsol) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol(stdVectorCasadiLinsol);
    }

    /**
     * Constructs a LinearSolverVector using another LinearSolverVector object.
     *
     * @param linearSolverVector the LinearSolverVector object used to initialize the vector.
     */
    public LinearSolverVector(LinearSolverVector linearSolverVector) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol(linearSolverVector.getCasADiObject());
    }

    /**
     * Constructs a LinearSolverVector with initial elements.
     *
     * @param initialElements an array of LinearSolver objects to initialize the vector.
     */
    public LinearSolverVector(LinearSolver... initialElements) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol();
        Arrays.stream(initialElements).forEach(
                solver -> this.stdVectorCasadiLinsol.add(solver.getCasADiObject())
        );
    }

    /**
     * Constructs a LinearSolverVector with initial elements from an iterable.
     *
     * @param initialElements an iterable of LinearSolver objects to initialize the vector.
     */
    public LinearSolverVector(Iterable<LinearSolver> initialElements) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol();
        initialElements.forEach(this::add);
    }

    /**
     * Constructs a LinearSolverVector with a specified count of a given LinearSolver.
     *
     * @param count         the number of times to add the LinearSolver.
     * @param linearSolver  the LinearSolver object to add.
     */
    public LinearSolverVector(int count, LinearSolver linearSolver) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol(count, linearSolver.getCasADiObject());
    }

    /**
     * Retrieves the LinearSolver at the specified index.
     *
     * @param index the index of the LinearSolver to retrieve.
     * @return the LinearSolver at the specified index.
     */
    @Override
    public LinearSolver get(int index) {
        return new LinearSolver(this.stdVectorCasadiLinsol.get(index));
    }

    /**
     * Sets the LinearSolver at the specified index.
     *
     * @param index   the index to set the LinearSolver.
     * @param element the LinearSolver to set.
     * @return the previous LinearSolver at the specified index.
     */
    @Override
    public LinearSolver set(int index, LinearSolver element) {
        return new LinearSolver(this.stdVectorCasadiLinsol.set(index, element.getCasADiObject()));
    }

    /**
     * Inserts a LinearSolver at the end of the vector.
     * Fluent interface variant from add().
     *
     * @param element the LinearSolver to insert.
     * @return the current LinearSolverVector instance.
     */
    @Override
    public LinearSolverVector insert(LinearSolver element) {
        this.stdVectorCasadiLinsol.add(element.getCasADiObject());
        return this;
    }

    /**
     * Inserts a LinearSolver at the specified index.
     * Fluent interface variant from add().
     *
     * @param index   the index to insert the LinearSolver.
     * @param element the LinearSolver to insert.
     * @return the current LinearSolverVector instance.
     */
    @Override
    public LinearSolverVector insert(int index, LinearSolver element) {
        this.stdVectorCasadiLinsol.add(index, element.getCasADiObject());
        return this;
    }

    /**
     * Clears the vector and adds a LinearSolver.
     *
     * @param element the LinearSolver to add.
     * @return the current LinearSolverVector instance.
     */
    @Override
    public LinearSolverVector clearAndAdd(LinearSolver element) {
        this.clear();
        return this.insert(element);
    }

    /**
     * Reserves space and adds a LinearSolver.
     *
     * @param n       the number of elements to reserve space for.
     * @param element the LinearSolver to add.
     * @return the current LinearSolverVector instance.
     */
    @Override
    public LinearSolverVector reserveAndAdd(long n, LinearSolver element) {
        this.reserve(n);
        return this.insert(element);
    }

    /**
     * Removes the LinearSolver at the specified index.
     *
     * @param index the index of the LinearSolver to remove.
     * @return the removed LinearSolver.
     */
    @Override
    public LinearSolver remove(int index) {
        return new LinearSolver(this.stdVectorCasadiLinsol.remove(index));
    }

    /**
     * Removes a range of LinearSolvers from the vector.
     *
     * @param fromIndex the starting index of the range to remove.
     * @param toIndex   the ending index of the range to remove.
     * @throws IndexOutOfBoundsException if the range is invalid.
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
     * Retrieves the size of the vector.
     *
     * @return the size of the vector.
     */
    @Override
    public int size() {
        return this.stdVectorCasadiLinsol.size();
    }

    /**
     * Retrieves the capacity of the vector.
     *
     * @return the capacity of the vector.
     */
    @Override
    public long capacity() {
        return this.stdVectorCasadiLinsol.capacity();
    }

    /**
     * Reserves space for a specified number of elements.
     *
     * @param n the number of elements to reserve space for.
     */
    @Override
    public void reserve(long n) {
        this.stdVectorCasadiLinsol.reserve(n);
    }

    /**
     * Checks if the vector is empty.
     *
     * @return true if the vector is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return this.stdVectorCasadiLinsol.isEmpty();
    }

    /**
     * Clears all elements from the vector.
     */
    @Override
    public void clear() {
        this.stdVectorCasadiLinsol.clear();
    }

    /**
     * Retrieves the underlying CasADi object.
     *
     * @return the StdVectorCasadiLinsol object representing the vector.
     */
    public StdVectorCasadiLinsol getCasADiObject() {
        return this.stdVectorCasadiLinsol;
    }

    /**
     * Retrieves a list of LinearSolver wrappers.
     *
     * @return a list of LinearSolver objects.
     */
    @Override
    public List<LinearSolver> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    /**
     * Adds a LinearSolver to the vector.
     *
     * @param element the LinearSolver to add.
     * @return true if the element was added successfully.
     */
    public boolean add(LinearSolver element) {
        return this.stdVectorCasadiLinsol.add(element.getCasADiObject());
    }

    /**
     * Adds a LinearSolver at the specified index.
     *
     * @param index   the index to add the LinearSolver.
     * @param element the LinearSolver to add.
     */
    public void add(int index, LinearSolver element) {
        this.stdVectorCasadiLinsol.add(index, element.getCasADiObject());
    }

    /**
     * Converts the vector to a string representation.
     *
     * @return a string representation of the vector.
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
