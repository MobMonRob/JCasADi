package de.dhbw.rahmlab.casadi.api.core.wrapper.sx;

import de.dhbw.rahmlab.casadi.api.core.interfaces.CasADiMap;
import de.dhbw.rahmlab.casadi.impl.casadi.SX;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToSX;

import java.util.*;
import java.util.stream.Collectors;

public class MapStringToSXWrapper extends AbstractMap<String, SXWrapper> implements CasADiMap<SXWrapper> {

    private final StdMapStringToSX stdMapStringToSX;

    public MapStringToSXWrapper() {
        this.stdMapStringToSX = new StdMapStringToSX();
    }

    public MapStringToSXWrapper(StdMapStringToSX other) {
        this.stdMapStringToSX = new StdMapStringToSX(other);
    }

    public MapStringToSXWrapper(MapStringToSXWrapper other) {
        this.stdMapStringToSX = new StdMapStringToSX(other.getCasADiObject());
    }

    public MapStringToSXWrapper(Map<? extends String, ? extends SXWrapper> other) {
        this.stdMapStringToSX = new StdMapStringToSX();
        other.forEach((key, value) -> {
            SX sxValue = value.getCasADiObject();
            this.stdMapStringToSX.put(key, sxValue);
        });
    }

    public int size() {
        return this.stdMapStringToSX.size();
    }

    public boolean containsKey(Object key) {
        return this.stdMapStringToSX.containsKey(key);
    }

    public SXWrapper get(Object key) {
        return new SXWrapper(this.stdMapStringToSX.get(key));
    }

    public SXWrapper put(String key, SXWrapper value) {
        return new SXWrapper(this.stdMapStringToSX.put(key, value.getCasADiObject()));
    }

    public SXWrapper remove(Object key) {
        return new SXWrapper(this.stdMapStringToSX.remove(key));
    }

    public Set<Entry<String, SXWrapper>> entrySet() {
        return this.stdMapStringToSX.entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(), new SXWrapper(entry.getValue())))
                .collect(Collectors.toSet());
    }

    public boolean isEmpty() {
        return this.stdMapStringToSX.isEmpty();
    }

    public void clear() {
        this.stdMapStringToSX.clear();
    }

    public StdMapStringToSX getCasADiObject() {
        return this.stdMapStringToSX;
    }

}
