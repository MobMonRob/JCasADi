package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Collection;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorSX;

import java.util.Arrays;

public class SXVectorCollection implements Collection<SXVectorCollection, SXVector> {

    StdVectorVectorSX stdVectorVectorSX;

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
        initialElements.forEach(this::add);
    }

    public SXVectorCollection(SXVectorCollection other) {
        this.stdVectorVectorSX = new StdVectorVectorSX(other.getCasADiObject());
    }

    public SXVectorCollection(int index, SXVector value) {
        this.stdVectorVectorSX = new StdVectorVectorSX(index, value.getCasADiObject());
    }

    public SXVectorCollection(StdVectorVectorSX stdVectorVectorSX) {
        this.stdVectorVectorSX = new StdVectorVectorSX(stdVectorVectorSX);
    }

    @Override
    public SXVector getVector(int index) {
        return new SXVector(this.stdVectorVectorSX.get(index));
    }

    @Override
    public SXVector setVector(int index, SXVector vector) {
        return new SXVector(this.stdVectorVectorSX.set(index, vector.getCasADiObject()));
    }

    @Override
    public SXVectorCollection add(SXVector vector) {
        this.stdVectorVectorSX.add(vector.getCasADiObject());
        return this;
    }

    @Override
    public SXVectorCollection add(int index, SXVector vector) {
        this.stdVectorVectorSX.add(index, vector.getCasADiObject());
        return this;
    }

    @Override
    public SXVector remove(int index) {
        return new SXVector(this.stdVectorVectorSX.remove(index));
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
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

}
