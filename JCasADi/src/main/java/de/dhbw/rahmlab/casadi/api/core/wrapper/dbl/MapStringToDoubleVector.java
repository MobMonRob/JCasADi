package de.dhbw.rahmlab.casadi.api.core.wrapper.dbl;

import de.dhbw.rahmlab.casadi.api.core.interfaces.CasADiMap;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToVectorDouble;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorDouble;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapStringToDoubleVector extends AbstractMap<String, DoubleVector> implements CasADiMap<DoubleVector> {

    private final StdMapStringToVectorDouble stdMapStringToVectorDouble;

    public MapStringToDoubleVector() {
        this.stdMapStringToVectorDouble = new StdMapStringToVectorDouble();
    }

    public MapStringToDoubleVector(StdMapStringToVectorDouble other) {
        this.stdMapStringToVectorDouble = new StdMapStringToVectorDouble(other);
    }

    public MapStringToDoubleVector(MapStringToDoubleVector other) {
        this.stdMapStringToVectorDouble = new StdMapStringToVectorDouble(other.getCasADiObject());
    }

    public MapStringToDoubleVector(Map<? extends String, ? extends DoubleVector> other) {
        this.stdMapStringToVectorDouble = new StdMapStringToVectorDouble();
        other.forEach((key, value) -> {
            StdVectorDouble stdVectorDouble = value.getCasADiObject();
            this.stdMapStringToVectorDouble.put(key, stdVectorDouble);
        });
    }

    public int size() {
        return this.stdMapStringToVectorDouble.size();
    }

    public boolean containsKey(Object key) {
        return this.stdMapStringToVectorDouble.containsKey(key);
    }

    public DoubleVector get(Object key) {
        return new DoubleVector(this.stdMapStringToVectorDouble.get(key));
    }

    public DoubleVector put(String key, DoubleVector vector) {
        return new DoubleVector(this.stdMapStringToVectorDouble.put(key, vector.getCasADiObject()));
    }

    public DoubleVector remove(Object key) {
        return new DoubleVector(this.stdMapStringToVectorDouble.remove(key));
    }

    public Set<Entry<String, DoubleVector>> entrySet() {
        return this.stdMapStringToVectorDouble.entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(), new DoubleVector(entry.getValue())))
                .collect(Collectors.toSet());
    }

    public boolean isEmpty() {
        return this.stdMapStringToVectorDouble.isEmpty();
    }

    public void clear() {
        this.stdMapStringToVectorDouble.clear();
    }

    public StdMapStringToVectorDouble getCasADiObject() {
        return this.stdMapStringToVectorDouble;
    }

}
