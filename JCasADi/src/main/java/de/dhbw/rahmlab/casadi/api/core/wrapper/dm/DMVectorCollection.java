package de.dhbw.rahmlab.casadi.api.core.wrapper.dm;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Collection;
import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXVectorCollection;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorVectorDM;

import java.util.Arrays;

public class DMVectorCollection implements Collection<DMVectorCollection, DMVector> {

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
        initialElements.forEach(this::add);
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
    public DMVector getVector(int index) {
        return new DMVector(this.stdVectorVectorDM.get(index));
    }

    @Override
    public DMVector setVector(int index, DMVector vector) {
        return new DMVector(this.stdVectorVectorDM.set(index, vector.getCasADiObject()));
    }

    @Override
    public DMVectorCollection add(DMVector vector) {
        this.stdVectorVectorDM.add(vector.getCasADiObject());
        return this;
    }

    @Override
    public DMVectorCollection add(int index, DMVector vector) {
        this.stdVectorVectorDM.add(index, vector.getCasADiObject());
        return this;
    }

    @Override
    public DMVector remove(int index) {
        return new DMVector(this.stdVectorVectorDM.remove(index));
    }

    @Override
    public void removeRange(int fromIndex, int toIndex) {
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

}
