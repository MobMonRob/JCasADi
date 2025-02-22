package de.dhbw.rahmlab.casadi.api.core.wrapper.std;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiSparsity;

import java.util.AbstractList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SparsityVector extends AbstractList<Sparsity> implements Vector<Sparsity> {

    private final StdVectorCasadiSparsity stdVectorCasadiSparsity;

    public SparsityVector() {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity();
    }

    public SparsityVector(Iterable<Sparsity> initialElements) {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity(initialElements);
    }

    public SparsityVector(Sparsity... initialElements) {
        this.stdVectorCasadiSparsity = new StdVectorCasadiSparsity(initialElements);
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
    public Sparsity get(int index) {
        return stdVectorCasadiSparsity.get(index);
    }

    @Override
    public Sparsity set(int index, Sparsity element) {
        return stdVectorCasadiSparsity.set(index, element);
    }

    @Override
    public SparsityVector insert(Sparsity element) {
        this.stdVectorCasadiSparsity.add(element);
        return this;
    }

    @Override
    public SparsityVector insert(int index, Sparsity element) {
        this.stdVectorCasadiSparsity.add(index, element);
        return this;
    }

    @Override
    public SparsityVector clearAndAdd(Sparsity element) {
        this.clear();
        return this.insert(element);
    }

    @Override
    public SparsityVector reserveAndAdd(long n, Sparsity element) {
        this.reserve(n);
        return this.insert(element);
    }

    @Override
    public Sparsity remove(int index) {
        return this.stdVectorCasadiSparsity.remove(index);
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
    public List<Sparsity> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(Sparsity element) {
        return this.stdVectorCasadiSparsity.add(element);
    }

    public void add(int index, Sparsity element) {
        this.stdVectorCasadiSparsity.add(index, element);
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
