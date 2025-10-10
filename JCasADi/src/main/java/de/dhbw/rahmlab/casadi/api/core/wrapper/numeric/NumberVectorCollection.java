package de.dhbw.rahmlab.casadi.api.core.wrapper.numeric;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberVectorCollection extends AbstractList<NumberVector> {

    private final List<NumberVector> numberVectorCollection;

    public NumberVectorCollection() {
        this.numberVectorCollection = new ArrayList<NumberVector>();
    }

    public NumberVectorCollection(NumberVector... initialElements) {
        this.numberVectorCollection = new ArrayList<>();
        Collections.addAll(numberVectorCollection, initialElements);
    }

    public NumberVectorCollection(Iterable<NumberVector> initialElements) {
        this.numberVectorCollection = new ArrayList<>();
        initialElements.forEach(this.numberVectorCollection::add);
    }

    public NumberVectorCollection(NumberVectorCollection other) {
        this.numberVectorCollection = other.getNumberVectorCollection();
    }

    public NumberVectorCollection(int count, NumberVector vector) {
        this.numberVectorCollection = new ArrayList<>(Collections.nCopies(count, vector));
    }

    public NumberVector get(int index) {
        return this.numberVectorCollection.get(index);
    }

    public NumberVector set(int index, NumberVector vector) {
        return this.numberVectorCollection.set(index, vector);
    }

    public NumberVectorCollection insert(NumberVector vector) {
        this.numberVectorCollection.add(vector);
        return this;
    }

    public NumberVectorCollection insert(int index, NumberVector vector) {
        this.numberVectorCollection.add(index, vector);
        return this;
    }

    public NumberVectorCollection clearAndAdd(NumberVector vector) {
        this.clear();
        return this.insert(vector);
    }

    public NumberVectorCollection reserveAndAdd(long n, NumberVector vector) {
        this.reserve(n);
        return this.insert(vector);
    }

    public NumberVectorCollection delete(int index) {
        this.numberVectorCollection.remove(index);
        return this;
    }

    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.numberVectorCollection.remove(i);
        }
    }

    public int size() {
        return this.numberVectorCollection.size();
    }

    public long capacity() {
        try {
            Field dataField = ArrayList.class.getDeclaredField("elementData");
            dataField.setAccessible(true);
            Object[] elementData = (Object[]) dataField.get(this.numberVectorCollection);
            return elementData.length;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void reserve(long n) {
        if (this.numberVectorCollection instanceof ArrayList) {
            ((ArrayList<NumberVector>) this.numberVectorCollection).ensureCapacity((int) n);
        } else {
            throw new UnsupportedOperationException("The underlying list is not an ArrayList.");
        }
    }

    public boolean isEmpty() {
        return this.numberVectorCollection.isEmpty();
    }

    public void clear() {
        this.numberVectorCollection.clear();
    }

    public List<NumberVector> getNumberVectorCollection() {
        return this.numberVectorCollection;
    }

    public boolean add(NumberVector vector) {
        return this.numberVectorCollection.add(vector);
    }

    public void add(int index, NumberVector vector) {
        this.numberVectorCollection.add(index, vector);
    }

    public NumberVector remove(int index) {
        return this.numberVectorCollection.remove(index);
    }

}
