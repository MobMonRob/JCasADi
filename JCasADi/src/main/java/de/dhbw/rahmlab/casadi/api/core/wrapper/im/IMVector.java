package de.dhbw.rahmlab.casadi.api.core.wrapper.im;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Vector;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IMVector extends AbstractList<IMWrapper> implements Vector<IMWrapper> {

    private final List<IMWrapper> elements;
    private int currentCapacity;

    public IMVector() {
        this.elements = new ArrayList<IMWrapper>();
        this.currentCapacity = 0;
    }

    public IMVector(IMWrapper... initialElements) {
        this.elements = Arrays.asList(initialElements);
        this.currentCapacity = elements.size();
    }

    public IMVector(Iterable<IMWrapper> initialElements) {
        this.elements = new ArrayList<>();
        initialElements.forEach(this.elements::add);
        this.currentCapacity = elements.size();
    }

    @Override
    public IMWrapper get(int index) {
        return this.elements.get(index);
    }

    @Override
    public IMWrapper set(int index, IMWrapper element) {
        return this.elements.set(index, element);
    }

    @Override
    public IMVector insert(IMWrapper element) {
        this.elements.add(element);
        return this;
    }

    @Override
    public IMVector insert(int index, IMWrapper element) {
        this.elements.add(index, element);
        return this;
    }

    @Override
    public IMVector clearAndAdd(IMWrapper element) {
        this.clear();
        return this.insert(element);
    }

    @Override
    public IMVector reserveAndAdd(int n, IMWrapper element) {
        this.reserve(n);
        return this.insert(element);
    }

    @Override
    public IMWrapper remove(int index) {
        return this.elements.remove(index);
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
        return this.elements.size();
    }

    @Override
    public long capacity() {
        return currentCapacity;
    }

    @Override
    public void reserve(int n) {
        if (n > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Cannot reserve more than Integer.MAX_VALUE elements.");
        }
        ((ArrayList<IMWrapper>) elements).ensureCapacity((int) n);
        currentCapacity = Math.max(currentCapacity, (int) n);
    }

    @Override
    public boolean isEmpty() {
        return this.elements.isEmpty();
    }

    @Override
    public void clear() {
        this.elements.clear();
    }

    @Override
    public List<IMWrapper> getWrappers() {
        return this.elements;
    }

    public boolean add(IMWrapper element) {
        return this.elements.add(element);
    }

    public void add(int index, IMWrapper element) {
        this.elements.add(index, element);
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
