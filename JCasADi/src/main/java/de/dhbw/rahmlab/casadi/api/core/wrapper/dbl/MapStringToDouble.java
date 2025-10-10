package de.dhbw.rahmlab.casadi.api.core.wrapper.dbl;


import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.interfaces.CasADiMap;
import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.MapStringToDMWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToDM;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapStringToDouble extends AbstractMap<String, Double> implements CasADiMap<Double> {

    private final StdMapStringToDM stdMapStringToDM;

    public MapStringToDouble() {
        this.stdMapStringToDM = new StdMapStringToDM();
    }

    public MapStringToDouble(MapStringToDMWrapper other) {
        this.stdMapStringToDM = new StdMapStringToDM(other.getCasADiObject());
    }

    public MapStringToDouble(Map<? extends String, ? extends Double> other) {
        this.stdMapStringToDM = new StdMapStringToDM();
        other.forEach((key, value) -> {
            DM dmValue = new DM(value);
            this.stdMapStringToDM.put(key, dmValue);
        });
    }

    public int size() {
        return this.stdMapStringToDM.size();
    }

    public boolean containsKey(Object key) {
        return this.stdMapStringToDM.containsKey(key);
    }

    public Double get(Object key) {
        return this.stdMapStringToDM.get(key).scalar();
    }

    public DMWrapper put(String key, DMWrapper value) {
        return new DMWrapper(this.stdMapStringToDM.put(key, value.getCasADiObject()));
    }

    public Double remove(Object key) {
        return this.stdMapStringToDM.remove(key).scalar();
    }

    public Set<Entry<String, Double>> entrySet() {
        return this.stdMapStringToDM.entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(), entry.getValue().scalar()))
                .collect(Collectors.toSet());
    }

    public boolean isEmpty() {
        return this.stdMapStringToDM.isEmpty();
    }

    public void clear() {
        this.stdMapStringToDM.clear();
    }

    public StdMapStringToDM getCasADiObject() {
        return this.stdMapStringToDM;
    }

    public MapStringToDMWrapper getWrapper() {
        return new MapStringToDMWrapper(this.stdMapStringToDM);
    }

}
