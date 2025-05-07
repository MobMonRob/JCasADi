package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Collection;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDM;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DMVectorCollection extends AbstractList<DMVector> implements Collection<DMVector> {

    private final StdVectorVectorDM stdVectorVectorDM;

    public DMVectorCollection() {
        this.stdVectorVectorDM = new StdVectorVectorDM();
    }

    public DMVectorCollection(DMVector... initialElements) {
        this.stdVectorVectorDM = new StdVectorVectorDM();
        Arrays.stream(initialElements).forEach(
                element -> this.stdVectorVectorDM.add(element.getCasADiObject())
        );
    }

    public DMVectorCollection(Iterable<DMVector> initialElements) {
        this.stdVectorVectorDM = new StdVectorVectorDM();
        initialElements.forEach(
                element -> this.stdVectorVectorDM.add(element.getCasADiObject())
        );
    }

    public DMVectorCollection(DMVectorCollection other) {
        this.stdVectorVectorDM = new StdVectorVectorDM(other.getCasADiObject());
    }

    public DMVectorCollection(int index, DMVector value) {
        this.stdVectorVectorDM = new StdVectorVectorDM(index, value.getCasADiObject());
    }

    public DMVectorCollection(StdVectorVectorDM stdVectorVectorDM) {
        this.stdVectorVectorDM = new StdVectorVectorDM(stdVectorVectorDM);
    }

    @Override
    public DMVector get(int index) {
        return new DMVector(this.stdVectorVectorDM.get(index));
    }

    @Override
    public DMVector set(int index, DMVector vector) {
        return new DMVector(this.stdVectorVectorDM.set(index, vector.getCasADiObject()));
    }

    @Override
    public DMVectorCollection insert(DMVector vector) {
        this.stdVectorVectorDM.add(vector.getCasADiObject());
        return this;
    }

    @Override
    public DMVectorCollection insert(int index, DMVector vector) {
        this.stdVectorVectorDM.add(index, vector.getCasADiObject());
        return this;
    }

    @Override
    public DMVectorCollection clearAndAdd(DMVector vector) {
        this.clear();
        return this.insert(vector);
    }

    @Override
    public DMVectorCollection reserveAndAdd(long n, DMVector vector) {
        this.reserve(n);
        return this.insert(vector);
    }

    @Override
    public DMVector remove(int index) {
        return new DMVector(this.stdVectorVectorDM.remove(index));
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.stdVectorVectorDM.remove(i);
        }
    }

    @Override
    public int size() {
        return this.stdVectorVectorDM.size();
    }

    @Override
    public long capacity() {
        return this.stdVectorVectorDM.capacity();
    }

    @Override
    public void reserve(long n) {
        this.stdVectorVectorDM.reserve(n);
    }

    @Override
    public boolean isEmpty() {
        return this.stdVectorVectorDM.isEmpty();
    }

    @Override
    public void clear() {
        this.stdVectorVectorDM.clear();
    }

    public StdVectorVectorDM getCasADiObject() {
        return this.stdVectorVectorDM;
    }

    @Override
    public List<DMVector> getVectors() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(DMVector vector) {
        return this.stdVectorVectorDM.add(vector.getCasADiObject());
    }

    public void add(int index, DMVector vector) {
        this.stdVectorVectorDM.add(index, vector.getCasADiObject());
    }

}
