package org.grimoire.util;

import java.util.AbstractMap;
import java.util.Map;

public final class Maps {
    
    private Maps() {}

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

}
