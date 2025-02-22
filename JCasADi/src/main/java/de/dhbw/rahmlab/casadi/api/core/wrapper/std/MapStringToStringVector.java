package de.dhbw.rahmlab.casadi.api.core.wrapper.std;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.CasADiMap;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToVectorString;
import de.dhbw.rahmlab.casadi.impl.std.StdVectorStdString;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapStringToStringVector extends AbstractMap<String, StringVector> implements CasADiMap<StringVector> {

    private final StdMapStringToVectorString stdMapStringToVectorString;

    public MapStringToStringVector() {
        this.stdMapStringToVectorString = new StdMapStringToVectorString();
    }

    public MapStringToStringVector(StdMapStringToVectorString other) {
        this.stdMapStringToVectorString = new StdMapStringToVectorString(other);
    }

    public MapStringToStringVector(MapStringToStringVector other) {
        this.stdMapStringToVectorString = new StdMapStringToVectorString(other.getCasADiObject());
    }

    public MapStringToStringVector(Map<? extends String, ? extends StringVector> other) {
        this.stdMapStringToVectorString = new StdMapStringToVectorString();
        other.forEach((key, value) -> {
            StdVectorStdString stdVectorStdString = value.getCasADiObject();
            this.stdMapStringToVectorString.put(key, stdVectorStdString);
        });
    }

    public int getSize() {
        return this.stdMapStringToVectorString.size();
    }

    public boolean containsKey(Object key) {
        return this.stdMapStringToVectorString.containsKey(key);
    }

    public StringVector get(Object key) {
        return new StringVector(this.stdMapStringToVectorString.get(key));
    }

    public StringVector put(String key, StringVector value) {
        return new StringVector(this.stdMapStringToVectorString.put(key, value.getCasADiObject()));
    }

    public StringVector remove(Object key) {
        return new StringVector(this.stdMapStringToVectorString.remove(key));
    }

    public Set<Entry<String, StringVector>> entrySet() {
        return this.stdMapStringToVectorString.entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(), new StringVector(entry.getValue())))
                .collect(Collectors.toSet());
    }

    public boolean isEmpty() {
        return this.stdMapStringToVectorString.isEmpty();
    }

    public void clear() {
        this.stdMapStringToVectorString.clear();
    }

    public StdMapStringToVectorString getCasADiObject() {
        return this.stdMapStringToVectorString;
    }

}
