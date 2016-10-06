package com.bitsfromspace.utils;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author chris
 * @since 28/09/2016.
 */
public class Cache <K,V> {

    private final Map<K,Item<V>> map;
    private final long timeoutMillis;
    private final Clock clock;

    public Cache(Clock clock, int timeout, TimeUnit timeoutUnit){
        this.clock = clock;
        this.timeoutMillis = timeoutUnit.toMillis(timeout);
        this.map = new HashMap<>();
    }

    public V getOrSet(K key, Function<K, V> supplier){
        return map.compute(key, (k, item) -> {
            if (item == null || isExpired(item)){
                return createItem(k, supplier);
            }
            return item;
        }).value;
    }

    private Item<V> createItem(K key, Function<K, V> supplier) {
        return new Item<>(supplier.apply(key), clock.millis());
    }

    private boolean isExpired(Item<V> item) {
        return clock.millis() - item.createdTime < timeoutMillis;
    }


    private static class Item<T>{
        private final T value;
        private final long createdTime;

        private Item(T value, long createdTime) {
            this.value = value;
            this.createdTime = createdTime;
        }
    }
}
