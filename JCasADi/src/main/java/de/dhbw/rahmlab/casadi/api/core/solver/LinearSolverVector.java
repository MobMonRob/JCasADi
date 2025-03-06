package de.dhbw.rahmlab.casadi.api.core.solver;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiLinsol;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinearSolverVector extends AbstractList<LinearSolver> implements Vector<LinearSolver> {

    private final StdVectorCasadiLinsol stdVectorCasadiLinsol;

    public LinearSolverVector() {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol();
    }

    public LinearSolverVector(StdVectorCasadiLinsol stdVectorCasadiLinsol) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol(stdVectorCasadiLinsol);
    }

    public LinearSolverVector(LinearSolverVector linearSolverVector) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol(linearSolverVector.getCasADiObject());
    }

    public LinearSolverVector(LinearSolver... initialElements) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol();
        Arrays.stream(initialElements).forEach(
                solver -> this.stdVectorCasadiLinsol.add(solver.getCasADiObject())
        );
    }

    public LinearSolverVector(Iterable<LinearSolver> initialElements) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol();
        initialElements.forEach(this::add);
    }

    public LinearSolverVector(int count, LinearSolver linearSolver) {
        this.stdVectorCasadiLinsol = new StdVectorCasadiLinsol(count, linearSolver.getCasADiObject());
    }

    @Override
    public LinearSolver get(int index) {
        return new LinearSolver(this.stdVectorCasadiLinsol.get(index));
    }

    @Override
    public LinearSolver set(int index, LinearSolver element) {
        return new LinearSolver(this.stdVectorCasadiLinsol.set(index, element.getCasADiObject()));
    }

    @Override
    public LinearSolverVector insert(LinearSolver element) {
        this.stdVectorCasadiLinsol.add(element.getCasADiObject());
        return this;
    }

    @Override
    public LinearSolverVector insert(int index, LinearSolver element) {
        this.stdVectorCasadiLinsol.add(index, element.getCasADiObject());
        return this;
    }

    @Override
    public LinearSolverVector clearAndAdd(LinearSolver element) {
        this.clear();
        return this.insert(element);
    }

    @Override
    public LinearSolverVector reserveAndAdd(long n, LinearSolver element) {
        this.reserve(n);
        return this.insert(element);
    }

    @Override
    public LinearSolver remove(int index) {
        return new LinearSolver(this.stdVectorCasadiLinsol.remove(index));
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.remove(i);
        }
    }

    @Override
    public int size() {
        return this.stdVectorCasadiLinsol.size();
    }

    @Override
    public long capacity() {
        return this.stdVectorCasadiLinsol.capacity();
    }

    @Override
    public void reserve(long n) {
        this.stdVectorCasadiLinsol.reserve(n);
    }

    @Override
    public boolean isEmpty() {
        return this.stdVectorCasadiLinsol.isEmpty();
    }

    @Override
    public void clear() {
        this.stdVectorCasadiLinsol.clear();
    }

    public StdVectorCasadiLinsol getCasADiObject() {
        return this.stdVectorCasadiLinsol;
    }

    @Override
    public List<LinearSolver> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(LinearSolver element) {
        return this.stdVectorCasadiLinsol.add(element.getCasADiObject());
    }

    public void add(int index, LinearSolver element) {
        this.stdVectorCasadiLinsol.add(index, element.getCasADiObject());
    }

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
