package ru.job4j.synch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public final class SingleLockList<T> implements Iterable<T> {
    private final List<T> list;

    public SingleLockList(final List<T> list) {
        this.list = copy(list);
    }
    @GuardedBy("this")
    public synchronized void add(T value) {
        list.add(value);
    }

    @GuardedBy("this")
    public synchronized T get(int index) {
        return copy(list).get(index);
    }

    @GuardedBy("this")
    @Override
    public synchronized Iterator<T> iterator() {
        return copy(list).iterator();
    }

    @GuardedBy("this")
    private synchronized List<T> copy(List<T> origin) {
        return new ArrayList<>(origin);
    }
}