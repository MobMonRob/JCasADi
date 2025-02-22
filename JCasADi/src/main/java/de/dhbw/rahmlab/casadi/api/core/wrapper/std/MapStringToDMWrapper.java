package de.dhbw.rahmlab.casadi.api.core.wrapper.std;

import de.dhbw.rahmlab.casadi.api.core.wrapper.dm.DMWrapper;
import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.CasADiMap;
import de.dhbw.rahmlab.casadi.impl.casadi.DM;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToDM;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MapStringToDMWrapper extends AbstractMap<String, DMWrapper> implements CasADiMap<DMWrapper> {

    private final StdMapStringToDM stdMapStringToDM;

    public MapStringToDMWrapper() {
        this.stdMapStringToDM = new StdMapStringToDM();
    }

    public MapStringToDMWrapper(StdMapStringToDM other) {
        this.stdMapStringToDM = new StdMapStringToDM(other);
    }

    public MapStringToDMWrapper(MapStringToDMWrapper other) {
        this.stdMapStringToDM = new StdMapStringToDM(other.getCasADiObject());
    }

    public MapStringToDMWrapper(Map<? extends String, ? extends DMWrapper> other) {
        this.stdMapStringToDM = new StdMapStringToDM();
        other.forEach((key, value) -> {
            DM dmValue = value.getCasADiObject();
            this.stdMapStringToDM.put(key, dmValue);
        });
    }

    public int size() {
        return this.stdMapStringToDM.size();
    }

    public boolean containsKey(Object key) {
        return this.stdMapStringToDM.containsKey(key);
    }

    public DMWrapper get(Object key) {
        return new DMWrapper(this.stdMapStringToDM.get(key));
    }

    public DMWrapper put(String key, DMWrapper value) {
        return new DMWrapper(this.stdMapStringToDM.put(key, value.getCasADiObject()));
    }

    public DMWrapper remove(Object key) {
        return new DMWrapper(this.stdMapStringToDM.remove(key));
    }

    public Set<Entry<String, DMWrapper>> entrySet() {
        return this.stdMapStringToDM.entrySet().stream()
                .map(entry -> new HashMap.SimpleEntry<>(entry.getKey(), new DMWrapper(entry.getValue())))
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

}
