package de.dhbw.rahmlab.casadi.api.core.wrapper.std;

import de.dhbw.rahmlab.casadi.api.core.wrapper.function.FunctionWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;
import de.dhbw.rahmlab.casadi.impl.casadi.Function;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorCasadiFunction;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionVector extends AbstractList<FunctionWrapper> implements Vector<FunctionWrapper> {

    public final StdVectorCasadiFunction stdVectorCasadiFunction;

    public FunctionVector() {
        this.stdVectorCasadiFunction = new StdVectorCasadiFunction();
    }

    public FunctionVector(Iterable<FunctionWrapper> initialElements) {
        this.stdVectorCasadiFunction = new StdVectorCasadiFunction();
        initialElements.forEach(this::add);
    }

    public FunctionVector(FunctionWrapper... initialElement) {
        this.stdVectorCasadiFunction = new StdVectorCasadiFunction();
        Arrays.stream(initialElement).forEach(value -> {
            Function function = value.getCasADiObject();
            this.stdVectorCasadiFunction.add(function);
        });
    }

    public FunctionVector(StdVectorCasadiFunction other) {
        this.stdVectorCasadiFunction = new StdVectorCasadiFunction(other);
    }

    public FunctionVector(FunctionVector other) {
        this.stdVectorCasadiFunction = new StdVectorCasadiFunction(other.getCasADiObject());
    }

    public FunctionVector(int count, FunctionWrapper functionWrapper) {
        this.stdVectorCasadiFunction = new StdVectorCasadiFunction(count, functionWrapper.getCasADiObject());
    }

    @Override
    public FunctionWrapper get(int index) {
        return new FunctionWrapper(this.stdVectorCasadiFunction.get(index));
    }

    @Override
    public FunctionWrapper set(int index, FunctionWrapper element) {
        return new FunctionWrapper(this.stdVectorCasadiFunction.set(index, element.getCasADiObject()));
    }

    @Override
    public FunctionVector insert(FunctionWrapper element) {
        this.stdVectorCasadiFunction.add(element.getCasADiObject());
        return this;
    }

    @Override
    public FunctionVector insert(int index, FunctionWrapper element) {
        this.stdVectorCasadiFunction.add(index, element.getCasADiObject());
        return this;
    }

    @Override
    public FunctionVector clearAndAdd(FunctionWrapper element) {
        this.clear();
        return this.insert(element);
    }

    @Override
    public FunctionVector reserveAndAdd(long n, FunctionWrapper element) {
        this.reserve(n);
        return this.insert(element);
    }

    @Override
    public FunctionWrapper remove(int index) {
        return new FunctionWrapper(this.stdVectorCasadiFunction.remove(index));
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
        return this.stdVectorCasadiFunction.size();
    }

    @Override
    public long capacity() {
        return this.stdVectorCasadiFunction.capacity();
    }

    @Override
    public void reserve(long n) {
        this.stdVectorCasadiFunction.reserve(n);
    }

    @Override
    public boolean isEmpty() {
        return this.stdVectorCasadiFunction.isEmpty();
    }

    @Override
    public void clear() {
        this.stdVectorCasadiFunction.clear();
    }

    public StdVectorCasadiFunction getCasADiObject() {
        return this.stdVectorCasadiFunction;
    }

    @Override
    public List<FunctionWrapper> getWrappers() {
        return IntStream.range(0, this.size())
                .mapToObj(this::get)
                .collect(Collectors.toList());
    }

    public boolean add(FunctionWrapper element) {
        return this.stdVectorCasadiFunction.add(element.getCasADiObject());
    }

    public void add(int index, FunctionWrapper element) {
        this.stdVectorCasadiFunction.add(index, element.getCasADiObject());
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
