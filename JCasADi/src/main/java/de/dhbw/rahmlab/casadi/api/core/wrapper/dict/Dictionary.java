package de.dhbw.rahmlab.casadi.api.core.wrapper.dict;

import de.dhbw.rahmlab.casadi.api.core.wrapper.generictype.CasADiGenericWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.std.Dict;

import java.util.*;
import java.util.stream.Collectors;

public class Dictionary extends AbstractMap<String, CasADiGenericWrapper> {

    private final Dict dictionary;

    public Dictionary() {
        this.dictionary = new Dict();
    }

    public Dictionary(Map<? extends String, ? extends CasADiGenericWrapper> other) {
        this.dictionary = new Dict();
        other.forEach((key, value) -> {
            GenericType genericValue = value.getCasADiObject();
            this.dictionary.put(key, genericValue);
        });
    }

    public Dictionary(Dict other) {
        this.dictionary = new Dict(other);
    }

    public Dictionary(Dictionary other) {
        this.dictionary = new Dict(other.getCasADiObject());
    }

    public static Dictionary of(KeyValue... keyValuePairs) {
        Dictionary dict = new Dictionary();
        for (KeyValue pair : keyValuePairs) {
            dict.put(pair.key(), pair.value());
        }
        return dict;
    }

    public int size() {
        return this.dictionary.size();
    }

    public boolean containsKey(Object key) {
        return this.dictionary.containsKey(key);
    }

    public CasADiGenericWrapper get(Object key) {
        return new CasADiGenericWrapper(this.dictionary.get(key));
    }

    public KeyValue getKeyValuePair(String key) {
        GenericType value = this.dictionary.get(key);
        if (value != null) {
            return new KeyValue(key, new CasADiGenericWrapper(value));
        }
        throw new NoSuchElementException("Key '" + key + "' does not exist in the dictionary.");
    }

    public CasADiGenericWrapper put(String key, CasADiGenericWrapper value) {
        return new CasADiGenericWrapper(this.dictionary.put(key, value.getCasADiObject()));
    }

    public Dictionary add(String key, CasADiGenericWrapper value) {
        this.dictionary.put(key, value.getCasADiObject());
        return this;
    }

    public CasADiGenericWrapper put(KeyValue pair) {
        return new CasADiGenericWrapper(this.dictionary.put(pair.key(), pair.value().getCasADiObject()));
    }

    public Dictionary add(KeyValue pair) {
        this.dictionary.put(pair.key(), pair.value().getCasADiObject());
        return this;
    }

    public CasADiGenericWrapper remove(Object key) {
        return new CasADiGenericWrapper(this.dictionary.remove(key));
    }

    public Dictionary removeEntry(Object key) {
        this.dictionary.remove(key);
        return this;
    }

    public CasADiGenericWrapper remove(KeyValue pair) {
        String key = pair.key();
        return new CasADiGenericWrapper(this.dictionary.remove(key));
    }

    public Dictionary removeEntry(KeyValue pair) {
        String key = pair.key();
        this.dictionary.remove(key);
        return this;
    }

    public Set<Entry<String, CasADiGenericWrapper>> entrySet() {
        return this.dictionary.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        entry.getKey(),
                        new CasADiGenericWrapper(entry.getValue())
                ))
                .collect(Collectors.toSet());
    }

    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    public void clear() {
        this.dictionary.clear();
    }

    public void putAll(Map<? extends String, ? extends CasADiGenericWrapper> m) {
        m.forEach((key, value) -> {
            GenericType genericValue = value.getCasADiObject();
            this.dictionary.put(key, genericValue);
        });
    }

    public void putAll(KeyValue... pairs) {
        Arrays.stream(pairs).forEach(
                pair -> this.dictionary.put(pair.key(), pair.value().getCasADiObject())
        );
    }

    public Set<String> keySet() {
        return this.dictionary.keySet();
    }

    public List<CasADiGenericWrapper> values() {
        return this.dictionary.values().stream()
                .map(CasADiGenericWrapper::new)
                .collect(Collectors.toList());
    }

    public Dict getCasADiObject() {
        return this.dictionary;
    }

}
