package de.dhbw.rahmlab.casadi.api.core.wrapper.std;

import de.dhbw.rahmlab.casadi.api.core.wrapper.interfaces.CasADiMap;
import de.dhbw.rahmlab.casadi.impl.casadi.Sparsity;
import de.dhbw.rahmlab.casadi.impl.std.StdMapStringToSparsity;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public class MapStringToSparsity extends AbstractMap<String, Sparsity> implements CasADiMap<Sparsity> {

    private final StdMapStringToSparsity stdMapStringToSparsity;

    public MapStringToSparsity() {
        this.stdMapStringToSparsity = new StdMapStringToSparsity();
    }

    public MapStringToSparsity(StdMapStringToSparsity other) {
        this.stdMapStringToSparsity = new StdMapStringToSparsity(other);
    }

    public MapStringToSparsity(MapStringToSparsity other) {
        this.stdMapStringToSparsity = new StdMapStringToSparsity(other.getCasADiObject());
    }

    public MapStringToSparsity(Map<? extends String, ? extends Sparsity> other) {
        this.stdMapStringToSparsity = new StdMapStringToSparsity();
        this.stdMapStringToSparsity.putAll(other);
    }

    public int size() {
        return this.stdMapStringToSparsity.size();
    }

    public boolean containsKey(Object key) {
        return this.stdMapStringToSparsity.containsKey(key);
    }

    public Sparsity get(Object key) {
        return this.stdMapStringToSparsity.get(key);
    }

    public Sparsity put(String key, Sparsity value) {
        return this.stdMapStringToSparsity.put(key, value);
    }

    public Sparsity remove(Object key) {
        return this.stdMapStringToSparsity.remove(key);
    }

    public Set<Entry<String, Sparsity>> entrySet() {
        return this.stdMapStringToSparsity.entrySet();
    }

    public boolean isEmpty() {
        return this.stdMapStringToSparsity.isEmpty();
    }

    public void clear() {
        this.stdMapStringToSparsity.clear();
    }

    public StdMapStringToSparsity getCasADiObject() {
        return this.stdMapStringToSparsity;
    }

}
