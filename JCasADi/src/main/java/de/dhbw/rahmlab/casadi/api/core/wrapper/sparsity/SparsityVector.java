package de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiSparsity;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SparsityVector extends AbstractList<SparsityWrapper> implements Vector<SparsityWrapper> {

    private final StdVectorCasadiSparsity stdVectorCasadiSparsity;

    public SparsityVector() {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity();
    }

    public SparsityVector(Iterable<SparsityWrapper> initialElements) {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity();
        initialElements.forEach(this::add);
    }

    public SparsityVector(SparsityWrapper... initialElements) {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity();
        this.addAll(Arrays.asList(initialElements));
    }

    public SparsityVector(StdVectorCasadiSparsity stdVectorCasadiSparsity) {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity(stdVectorCasadiSparsity);
    }

    public SparsityVector(SparsityVector sparsityVector) {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity(sparsityVector.getCasADiObject());
    }

    public SparsityVector(int index, Sparsity value) {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity(index, value);
    }

    @Override
    public SparsityWrapper get(int index) {
        return new SparsityWrapper(this.stdVectorCasadiSparsity.get(index));
    }

    @Override
    public SparsityWrapper set(int index, SparsityWrapper element) {
        return new SparsityWrapper(this.stdVectorCasadiSparsity.set(index, element.getCasADiObject()));
    }

    @Override
    public SparsityVector insert(SparsityWrapper element) {
        this.stdVectorCasadiSparsity.add(element.getCasADiObject());
        return this;
    }

    @Override
    public SparsityVector insert(int index, SparsityWrapper element) {
        this.stdVectorCasadiSparsity.add(index, element.getCasADiObject());
        return this;
    }

    @Override
    public SparsityVector clearAndAdd(SparsityWrapper element) {
        this.clear();
        return this.insert(element);
    }

    @Override
    public SparsityVector reserveAndAdd(long n, SparsityWrapper element) {
        this.reserve(n);
        return this.insert(element);
    }

    @Override
    public SparsityWrapper remove(int index) {
        return new SparsityWrapper(this.stdVectorCasadiSparsity.remove(index));
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
        return this.stdVectorCasadiSparsity.size();
    }

    @Override
    public long capacity() {
        return this.stdVectorCasadiSparsity.capacity();
    }

    @Override
    public void reserve(long n) {
        this.stdVectorCasadiSparsity.reserve(n);
    }

    @Override
    public boolean isEmpty() {
        return this.stdVectorCasadiSparsity.isEmpty();
    }

    @Override
    public void clear() {
        this.stdVectorCasadiSparsity.clear();
    }

    public StdVectorCasadiSparsity getCasADiObject() {
        return this.stdVectorCasadiSparsity;
    }

    @Override
    public List<SparsityWrapper> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(SparsityWrapper element) {
        return this.stdVectorCasadiSparsity.add(element.getCasADiObject());
    }

    public void add(int index, SparsityWrapper element) {
        this.stdVectorCasadiSparsity.add(index, element.getCasADiObject());
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
