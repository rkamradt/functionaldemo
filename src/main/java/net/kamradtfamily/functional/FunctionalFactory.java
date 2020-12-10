package net.kamradtfamily.functional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FunctionalFactory<T> {
    Map<String, Supplier<T>> maker = new HashMap<>();

    T build(String type) {
        if(!maker.containsKey(type)) {
            throw new IllegalArgumentException("type " + " is not known");
        }
        return maker.get(type).get();
    }

    void add(String type, Supplier<T> builder) {
        maker.put(type, builder);
    }

}
