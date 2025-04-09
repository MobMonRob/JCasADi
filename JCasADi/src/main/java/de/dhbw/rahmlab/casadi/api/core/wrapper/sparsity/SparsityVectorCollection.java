package de.dhbw.rahmlab.casadi.api.core.wrapper.sparsity;

import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorCasadiSparsity;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Collection;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SparsityVectorCollection extends AbstractList<SparsityVector> implements Collection<SparsityVector> {

    private final StdVectorVectorCasadiSparsity stdVectorVectorCasadiSparsity;

    public SparsityVectorCollection() {
        this.stdVectorVectorCasadiSparsity = new StdVectorVectorCasadiSparsity();
    }

    public SparsityVectorCollection(SparsityVector... initialElements) {
        this.stdVectorVectorCasadiSparsity = new StdVectorVectorCasadiSparsity();
        Arrays.stream(initialElements).forEach(
                vector -> this.stdVectorVectorCasadiSparsity.add(vector.getCasADiObject())
        );
    }

    public SparsityVectorCollection(Iterable<SparsityVector> initialElements) {
        this.stdVectorVectorCasadiSparsity = new StdVectorVectorCasadiSparsity();
        initialElements.forEach(
                vector -> this.stdVectorVectorCasadiSparsity.add(vector.getCasADiObject())
        );
    }

    public SparsityVectorCollection(StdVectorVectorCasadiSparsity other) {
        this.stdVectorVectorCasadiSparsity = new StdVectorVectorCasadiSparsity(other);
    }

    public SparsityVectorCollection(SparsityVectorCollection other) {
        this.stdVectorVectorCasadiSparsity = new StdVectorVectorCasadiSparsity(other.getCasADiObject());
    }

    public SparsityVectorCollection(int count, SparsityVector vector) {
        this.stdVectorVectorCasadiSparsity = new StdVectorVectorCasadiSparsity(count, vector.getCasADiObject());
    }

    @Override
    public SparsityVector get(int index) {
        return new SparsityVector(this.stdVectorVectorCasadiSparsity.get(index));
    }

    @Override
    public SparsityVector set(int index, SparsityVector vector) {
        return new SparsityVector(this.stdVectorVectorCasadiSparsity.set(index, vector.getCasADiObject()));
    }

    @Override
    public SparsityVectorCollection insert(SparsityVector vector) {
        this.stdVectorVectorCasadiSparsity.add(vector.getCasADiObject());
        return this;
    }

    @Override
    public SparsityVectorCollection insert(int index, SparsityVector vector) {
        this.stdVectorVectorCasadiSparsity.add(vector.getCasADiObject());
        return this;
    }

    @Override
    public SparsityVectorCollection clearAndAdd(SparsityVector vector) {
        this.clear();
        return this.insert(vector);
    }

    @Override
    public SparsityVectorCollection reserveAndAdd(long n, SparsityVector vector) {
        this.reserve(n);
        return this.insert(vector);
    }

    @Override
    public SparsityVector remove(int index) {
        return new SparsityVector(this.stdVectorVectorCasadiSparsity.remove(index));
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorVectorCasadiSparsity.remove(i);
        }
    }

    @Override
    public int size() {
        return this.stdVectorVectorCasadiSparsity.size();
    }

    @Override
    public long capacity() {
        return this.stdVectorVectorCasadiSparsity.capacity();
    }

    @Override
    public void reserve(long n) {
        this.stdVectorVectorCasadiSparsity.reserve(n);
    }

    @Override
    public boolean isEmpty() {
        return this.stdVectorVectorCasadiSparsity.isEmpty();
    }

    @Override
    public void clear() {
        this.stdVectorVectorCasadiSparsity.clear();
    }

    public StdVectorVectorCasadiSparsity getCasADiObject() {
        return this.stdVectorVectorCasadiSparsity;
    }

    @Override
    public List<SparsityVector> getVectors() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(SparsityVector vector) {
        return this.stdVectorVectorCasadiSparsity.add(vector.getCasADiObject());
    }

    public void add(int index, SparsityVector vector) {
        this.stdVectorVectorCasadiSparsity.add(index, vector.getCasADiObject());
    }

}
