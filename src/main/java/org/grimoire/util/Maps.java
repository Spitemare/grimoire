package org.grimoire.util;

import static java.util.stream.Collectors.toMap;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

public final class Maps {

    private Maps() {}

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> mapOf(Map.Entry<K, V>... entries) {
        return Stream.of(entries).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @SafeVarargs
    public static <K, V> Map<K, V> unmodifiableMapOf(Map.Entry<K, V>... entries) {
        return Collections.unmodifiableMap(mapOf(entries));
    }

}
