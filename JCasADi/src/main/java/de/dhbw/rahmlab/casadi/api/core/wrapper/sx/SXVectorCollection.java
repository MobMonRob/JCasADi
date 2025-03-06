package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Collection;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorSX;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SXVectorCollection extends AbstractList<SXVector> implements Collection<SXVector> {

    private final StdVectorVectorSX stdVectorVectorSX;

    public SXVectorCollection() {
        this.stdVectorVectorSX = new StdVectorVectorSX();
    }

    public SXVectorCollection(SXVector... initialElements) {
        this.stdVectorVectorSX = new StdVectorVectorSX();
        Arrays.stream(initialElements).forEach(
                element -> this.stdVectorVectorSX.add(element.getCasADiObject())
        );
    }

    public SXVectorCollection(Iterable<SXVector> initialElements) {
        this.stdVectorVectorSX = new StdVectorVectorSX();
        initialElements.forEach(
                element -> this.stdVectorVectorSX.add(element.getCasADiObject())
        );
    }

    public SXVectorCollection(SXVectorCollection other) {
        this.stdVectorVectorSX = new StdVectorVectorSX(other.getCasADiObject());
    }

    public SXVectorCollection(int index, SXVector vector) {
        this.stdVectorVectorSX = new StdVectorVectorSX(index, vector.getCasADiObject());
    }

    public SXVectorCollection(StdVectorVectorSX stdVectorVectorSX) {
        this.stdVectorVectorSX = new StdVectorVectorSX(stdVectorVectorSX);
    }

    @Override
    public SXVector get(int index) {
        return new SXVector(this.stdVectorVectorSX.get(index));
    }

    @Override
    public SXVector set(int index, SXVector vector) {
        return new SXVector(this.stdVectorVectorSX.set(index, vector.getCasADiObject()));
    }

    @Override
    public SXVectorCollection insert(SXVector vector) {
        this.stdVectorVectorSX.add(vector.getCasADiObject());
        return this;
    }

    @Override
    public SXVectorCollection insert(int index, SXVector vector) {
        this.stdVectorVectorSX.add(index, vector.getCasADiObject());
        return this;
    }

    @Override
    public SXVectorCollection clearAndAdd(SXVector vector) {
        this.clear();
        return this.insert(vector);
    }

    @Override
    public SXVectorCollection reserveAndAdd(long n, SXVector vector) {
        this.reserve(n);
        return this.insert(vector);
    }

    @Override
    public SXVector remove(int index) {
        return new SXVector(this.stdVectorVectorSX.remove(index));
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorVectorSX.remove(i);
        }
    }

    @Override
    public int size() {
        return this.stdVectorVectorSX.size();
    }

    @Override
    public long capacity() {
        return this.stdVectorVectorSX.capacity();
    }

    @Override
    public void reserve(long n) {
        this.stdVectorVectorSX.reserve(n);
    }

    @Override
    public boolean isEmpty() {
        return this.stdVectorVectorSX.isEmpty();
    }

    @Override
    public void clear() {
        this.stdVectorVectorSX.clear();
    }

    public StdVectorVectorSX getCasADiObject() {
        return this.stdVectorVectorSX;
    }

    @Override
    public List<SXVector> getVectors() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(SXVector vector) {
        return this.stdVectorVectorSX.add(vector.getCasADiObject());
    }

    public void add(int index, SXVector vector) {
        this.stdVectorVectorSX.add(index, vector.getCasADiObject());
    }

}
