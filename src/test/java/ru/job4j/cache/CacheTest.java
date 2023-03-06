package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {
    @Test
    void whenAddBaseItem() {
        Cache cache = new Cache();
        Base base = new Base(1, 0, "Base 1");
        assertThat(cache.add(base)).isTrue();
    }

    @Test
    void whenAddBaseWithSameIdTwoTimes() {
        Cache cache = new Cache();
        Base base = new Base(1, 0, "Base 1");
        Base baseSameId = new Base(1, 5, "Base 2");
        cache.add(base);
        assertThat(cache.add(baseSameId)).isFalse();
    }

    @Test
    void whenBaseBeenSequentlyUpdated() {
        Cache cache = new Cache();
        Base base = new Base(1, 0, "Base 1");
        cache.add(base);
        Base base2 = new Base(1, 0, "Base 2");
        cache.add(base2);
        assertThat(cache.update(base2)).isTrue();
    }

    @Test
    void whenUpdateWithDiffVersionsThenExceptionOccurs() {
        Cache cache = new Cache();
        Base base = new Base(1, 0, "Base 1");
        cache.add(base);
        Base base2 = new Base(1, 1, "Base 2");
        cache.add(base2);
        assertThatThrownBy(() -> cache.update(base2)).isInstanceOf(OptimisticException.class);
    }

    @Test
    void whenDeleteBase() {
        Cache cache = new Cache();
        Base base = new Base(1, 0, "Base 1");
        cache.add(base);
        cache.delete(base);
        assertThat(cache.add(base)).isTrue();
    }
}