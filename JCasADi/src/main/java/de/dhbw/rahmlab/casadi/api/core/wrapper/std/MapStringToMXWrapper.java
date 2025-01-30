package de.dhbw.rahmlab.casadi.api.core.wrapper.std;

import de.dhbw.rahmlab.casadi.api.core.wrapper.mx.MXWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.MX;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToMX;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapStringToMXWrapper {

    private final StdMapStringToMX stdMapStringToMX;

    public MapStringToMXWrapper() {
        this.stdMapStringToMX = new StdMapStringToMX();
    }

    public MapStringToMXWrapper(StdMapStringToMX other) {
        this.stdMapStringToMX = other;
    }

    public MapStringToMXWrapper(Map<String, MXWrapper> other) {
        this.stdMapStringToMX = new StdMapStringToMX();
        other.forEach((key, value) -> {
            MX mxValue = value.getCasADiObject();
            this.stdMapStringToMX.put(key, mxValue);
        });
    }

    public int getSize() {
        return this.stdMapStringToMX.size();
    }

    public boolean containsKey(String key) {
        return this.stdMapStringToMX.containsKey(key);
    }

    public MXWrapper get(String key) {
        return new MXWrapper(this.stdMapStringToMX.get(key));
    }

    public MXWrapper put(String key, MXWrapper value) {
        return new MXWrapper(this.stdMapStringToMX.put(key, value.getCasADiObject()));
    }

    public MXWrapper remove(String key) {
        return new MXWrapper(this.stdMapStringToMX.remove(key));
    }

    public Set<Map.Entry<String, MXWrapper>> entrySet() {
        return this.stdMapStringToMX.entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(), new MXWrapper(entry.getValue())))
                .collect(Collectors.toSet());
    }

    public boolean isEmpty() {
        return this.stdMapStringToMX.isEmpty();
    }

    public void clear() {
        this.stdMapStringToMX.clear();
    }

    public StdMapStringToMX getCasADiObject() {
        return this.stdMapStringToMX;
    }

}
