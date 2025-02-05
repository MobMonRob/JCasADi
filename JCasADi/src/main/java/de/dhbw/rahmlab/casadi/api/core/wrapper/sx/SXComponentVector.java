package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiSXElem;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SXComponentVector extends AbstractList<SXComponent> implements Vector<SXComponent> {

    private final StdVectorCasadiSXElem stdVectorCasadiSXElem;

    public SXComponentVector() {
        this.stdVectorCasadiSXElem = new StdVectorCasadiSXElem();
    }

    public SXComponentVector(Iterable<SXComponent> initialElements) {
        this.stdVectorCasadiSXElem = new StdVectorCasadiSXElem();
        initialElements.forEach(this::add);
    }

    public SXComponentVector(SXComponent... initialElements) {
        this.stdVectorCasadiSXElem = new StdVectorCasadiSXElem();
        Arrays.stream(initialElements).forEach(this::add);
    }

    public SXComponentVector(StdVectorCasadiSXElem stdVectorCasadiSXElem) {
        this.stdVectorCasadiSXElem = new StdVectorCasadiSXElem(stdVectorCasadiSXElem);
    }

    public SXComponentVector(SXComponentVector sxComponentVector) {
        this.stdVectorCasadiSXElem = new StdVectorCasadiSXElem(sxComponentVector.getCasADiObject());
    }

    public SXComponentVector(int count, SXComponent sxComponent) {
        this.stdVectorCasadiSXElem = new StdVectorCasadiSXElem(count, sxComponent.getCasADiObject());
    }

    @Override
    public SXComponent get(int index) {
        return new SXComponent(this.stdVectorCasadiSXElem.get(index));
    }

    @Override
    public SXComponent set(int index, SXComponent element) {
        return new SXComponent(this.stdVectorCasadiSXElem.set(index, element.getCasADiObject()));
    }

    @Override
    public SXComponentVector insert(SXComponent element) {
        this.stdVectorCasadiSXElem.add(element.getCasADiObject());
        return this;
    }

    @Override
    public SXComponentVector insert(int index, SXComponent element) {
        this.stdVectorCasadiSXElem.add(index, element.getCasADiObject());
        return this;
    }

    @Override
    public SXComponentVector clearAndAdd(SXComponent element) {
        this.clear();
        return this.insert(element);
    }

    @Override
    public SXComponentVector reserveAndAdd(long n, SXComponent element) {
        this.reserve(n);
        return this.insert(element);
    }

    @Override
    public SXComponent remove(int index) {
        return new SXComponent(this.stdVectorCasadiSXElem.remove(index));
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
        return this.stdVectorCasadiSXElem.size();
    }

    @Override
    public long capacity() {
        return this.stdVectorCasadiSXElem.capacity();
    }

    @Override
    public void reserve(long n) {
        this.stdVectorCasadiSXElem.reserve(n);
    }

    @Override
    public boolean isEmpty() {
        return this.stdVectorCasadiSXElem.isEmpty();
    }

    @Override
    public void clear() {
        this.stdVectorCasadiSXElem.clear();
    }

    public StdVectorCasadiSXElem getCasADiObject() {
        return this.stdVectorCasadiSXElem;
    }

    @Override
    public List<SXComponent> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(SXComponent element) {
        return this.stdVectorCasadiSXElem.add(element.getCasADiObject());
    }

    public void add(int index, SXComponent element) {
        this.stdVectorCasadiSXElem.add(index, element.getCasADiObject());
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
