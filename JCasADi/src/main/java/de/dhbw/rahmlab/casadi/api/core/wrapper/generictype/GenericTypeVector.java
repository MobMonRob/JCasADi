package de.dhbw.rahmlab.casadi.api.core.wrapper.generictype;

import de.dhbw.rahmlab.casadi.api.core.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiGenericType;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenericTypeVector extends AbstractList<CasADiGenericWrapper> implements Vector<CasADiGenericWrapper> {

    private final StdVectorCasadiGenericType stdVectorCasadiGenericType;

    public GenericTypeVector() {
        this.stdVectorCasadiGenericType = new StdVectorCasadiGenericType();
    }

    public GenericTypeVector(StdVectorCasadiGenericType other) {
        this.stdVectorCasadiGenericType = new StdVectorCasadiGenericType(other);
    }

    public GenericTypeVector(GenericTypeVector other) {
        this.stdVectorCasadiGenericType = new StdVectorCasadiGenericType(other.getCasADiObject());
    }

    public GenericTypeVector(CasADiGenericWrapper... initialElements) {
        this.stdVectorCasadiGenericType = new StdVectorCasadiGenericType();
        Arrays.stream(initialElements).forEach(e -> {
            this.stdVectorCasadiGenericType.add(e.getCasADiObject());
        });
    }

    public GenericTypeVector(Iterable<CasADiGenericWrapper> initialElement) {
        this.stdVectorCasadiGenericType = new StdVectorCasadiGenericType();
        initialElement.forEach(e -> {
            this.stdVectorCasadiGenericType.add(e.getCasADiObject());
        });
    }

    public GenericTypeVector(Number... initialElements) {
        this.stdVectorCasadiGenericType = new StdVectorCasadiGenericType();
        Arrays.stream(initialElements).forEach(e -> {
            GenericType newGenericType = new GenericType(e.doubleValue());
            this.stdVectorCasadiGenericType.add(newGenericType);
        });
    }

    public GenericTypeVector(int count, CasADiGenericWrapper element) {
        this.stdVectorCasadiGenericType = new StdVectorCasadiGenericType(count, element.getCasADiObject());
    }

    @Override
    public CasADiGenericWrapper get(int index) {
        return new CasADiGenericWrapper(this.stdVectorCasadiGenericType.get(index));
    }

    @Override
    public CasADiGenericWrapper set(int index, CasADiGenericWrapper element) {
        return new CasADiGenericWrapper(this.stdVectorCasadiGenericType.set(index, element.getCasADiObject()));
    }

    @Override
    public GenericTypeVector insert(CasADiGenericWrapper element) {
        this.stdVectorCasadiGenericType.add(element.getCasADiObject());
        return this;
    }

    @Override
    public GenericTypeVector insert(int index, CasADiGenericWrapper element) {
        this.stdVectorCasadiGenericType.add(index, element.getCasADiObject());
        return this;
    }

    @Override
    public GenericTypeVector clearAndAdd(CasADiGenericWrapper element) {
        this.clear();
        return this.insert(element);
    }

    @Override
    public GenericTypeVector reserveAndAdd(int n, CasADiGenericWrapper element) {
        this.reserve(n);
        return this.insert(element);
    }

    @Override
    public CasADiGenericWrapper remove(int index) {
        return new CasADiGenericWrapper(this.stdVectorCasadiGenericType.remove(index));
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
        return this.stdVectorCasadiGenericType.size();
    }

    @Override
    public long capacity() {
        return this.stdVectorCasadiGenericType.capacity();
    }

    @Override
    public void reserve(int n) {
        this.stdVectorCasadiGenericType.reserve(n);
    }

    @Override
    public boolean isEmpty() {
        return this.stdVectorCasadiGenericType.isEmpty();
    }

    @Override
    public void clear() {
        this.stdVectorCasadiGenericType.clear();
    }

    public StdVectorCasadiGenericType getCasADiObject() {
        return this.stdVectorCasadiGenericType;
    }

    @Override
    public List<CasADiGenericWrapper> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(CasADiGenericWrapper element) {
        return this.stdVectorCasadiGenericType.add(element.getCasADiObject());
    }

    public void add(int index, CasADiGenericWrapper element) {
        this.stdVectorCasadiGenericType.add(index, element.getCasADiObject());
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
