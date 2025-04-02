package de.dhbw.rahmlab.casadi.api.core.wrapper.numeric;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.Vector;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class NumberVector extends AbstractList<NumberWrapper> {

    private final List<NumberWrapper> numberVector;

    public NumberVector() {
        this.numberVector = new ArrayList<>();
    }

    public NumberVector(Number... initialElements) {
        this.numberVector = new ArrayList<>();
        Arrays.stream(initialElements).forEach(number -> this.numberVector.add(new NumberWrapper(number)));
    }

    public NumberVector(Iterable<? extends Number> initialElements) {
        this.numberVector = new ArrayList<>();
        initialElements.forEach(element -> this.numberVector.add(new NumberWrapper(element)));
    }

    public NumberVector(NumberVector other) {
        this.numberVector = other.getUnderlyingNumberWrappers();
    }

    public NumberVector(int count, NumberWrapper other) {
        this.numberVector = new ArrayList<>(Collections.nCopies(count, other));
    }

    public NumberWrapper get(int index) {
        return this.numberVector.get(index);
    }

    public Number get(int index, String datatype) {
        String lowerCaseString = datatype.toLowerCase();
        return switch (lowerCaseString) {
            case "int", "integer" -> this.numberVector.get(index).getIntValue();
            case "long" -> this.numberVector.get(index).getLongValue();
            case "float" -> this.numberVector.get(index).getFloatValue();
            case "double" -> this.numberVector.get(index).getDoubleValue();
            case "short" -> this.numberVector.get(index).getShortValue();
            default -> this.numberVector.get(index).getUnderlyingNumberObject();
        };
    }

    public NumberWrapper set(int index, Number element) {
        return this.numberVector.set(index, new NumberWrapper(element));
    }

    public NumberVector insert(Number element) {
        this.numberVector.add(new NumberWrapper(element));
        return this;
    }

    public NumberVector insert(int index, Number element) {
        this.numberVector.add(index, new NumberWrapper(element));
        return this;
    }

    public NumberVector clearAndAdd(Number element) {
        this.clear();
        return this.insert(element);
    }

    public NumberVector reserveAndAdd(long n, Number element) {
        this.reserve(n);
        return this.insert(element);
    }

    public NumberVector delete(int index) {
        this.numberVector.remove(index);
        return this;
    }

    // TODO: Implement Fluent-Interface for remove (delete)

    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size() || fromIndex >= toIndex) {
            throw new IndexOutOfBoundsException("Invalid range: fromIndex=" + fromIndex + ", toIndex=" + toIndex);
        }

        for (int i = toIndex - 1; i >= fromIndex; i--) {
            this.remove(i);
        }
    }

    public int size() {
        return this.numberVector.size();
    }

    public long capacity() {
        try {
            Field dataField = ArrayList.class.getDeclaredField("elementData");
            dataField.setAccessible(true);
            Object[] elementData = (Object[]) dataField.get(this.numberVector);
            return elementData.length;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void reserve(long n) {
        NumberWrapper number = new NumberWrapper(n);
        if (this.numberVector instanceof ArrayList) {
            ((ArrayList<NumberWrapper>) this.numberVector).ensureCapacity(number.getIntValue());
        } else {
            throw new UnsupportedOperationException("The underlying list is not an ArrayList.");
        }
    }

    public boolean isEmpty() {
        return this.numberVector.isEmpty();
    }

    public void clear() {
        this.numberVector.clear();
    }

    public List<NumberWrapper> getUnderlyingNumberWrappers() {
        return this.numberVector;
    }

    public List<Number> getUnderlyingNumberWrappersAsNumberObjects() {
        return this.numberVector.stream()
                .map(NumberWrapper::getUnderlyingNumberObject)
                .collect(Collectors.toList());
    }

    public boolean add(Number element) {
        return this.numberVector.add(new NumberWrapper(element));
    }

    public void add(int index, Number element) {
        this.numberVector.add(index, new NumberWrapper(element));
    }

    public NumberWrapper remove(int index) {
        return this.numberVector.remove(index);
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
