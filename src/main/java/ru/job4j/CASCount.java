package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        int current;
        do {
            current = count.get();
        } while (!count.compareAndSet(current, current + 1));
    }

    public int get() {
        if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return count.get();
    }
}