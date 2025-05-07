package de.dhbw.rahmlab.casadi.api.core.wrapper.im;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Collection;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IMVectorCollection extends AbstractList<IMVector> implements Collection<IMVector> {

    private final List<IMVector> imVectorList;
    private int currentCapacity;

    public IMVectorCollection() {
        this.imVectorList = new ArrayList<>();
        this.currentCapacity = 0;
    }

    public IMVectorCollection(IMVector... initialElements) {
        this.imVectorList = Arrays.asList(initialElements);
        this.currentCapacity = imVectorList.size();
    }

    public IMVectorCollection(Iterable<IMVector> initialElements) {
        this.imVectorList = new ArrayList<>();
        initialElements.forEach(this.imVectorList::add);
        this.currentCapacity = imVectorList.size();
    }

    @Override
    public IMVector get(int index) {
        return new IMVector(this.imVectorList.get(index));
    }

    @Override
    public IMVector set(int index, IMVector element) {
        return new IMVector(this.imVectorList.set(index, element));
    }

    @Override
    public IMVectorCollection insert(IMVector vector) {
        this.imVectorList.add(vector);
        return this;
    }

    @Override
    public IMVectorCollection insert(int index, IMVector vector) {
        this.imVectorList.add(index, vector);
        return this;
    }

    @Override
    public IMVectorCollection clearAndAdd(IMVector vector) {
        this.clear();
        return this.insert(vector);
    }

    @Override
    public IMVectorCollection reserveAndAdd(long n, IMVector vector) {
        this.reserve(n);
        return this.insert(vector);
    }

    @Override
    public IMVector remove(int index) {
        return new IMVector(this.imVectorList.remove(index));
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.imVectorList.remove(i);
        }
    }

    @Override
    public int size() {
        return this.imVectorList.size();
    }

    @Override
    public long capacity() {
        return currentCapacity;
    }

    @Override
    public void reserve(long n) {
        if (n > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Cannot reserve more than Integer.MAX_VALUE elements.");
        }
        ((ArrayList<IMVector>) imVectorList).ensureCapacity((int) n);
        currentCapacity = Math.max(currentCapacity, (int) n);
    }

    @Override
    public boolean isEmpty() {
        return this.imVectorList.isEmpty();
    }

    @Override
    public void clear() {
        this.imVectorList.clear();
    }

    @Override
    public List<IMVector> getVectors() {
        return this.imVectorList;
    }

    public boolean add(IMVector vector) {
        return this.imVectorList.add(vector);
    }

    public void add(int index, IMVector vector) {
        this.imVectorList.add(index, vector);
    }

}
