package com.cwsoft.messaging;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public interface Stream<K, V> {
    Stream<K, V> filter(BiPredicate<K, V> predicate);

    void forEach(BiConsumer<K, V> action);

    void start();

    void close();
}
