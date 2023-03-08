package ru.job4j.fork;

import java.util.concurrent.RecursiveTask;

public class IndexFinder<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T el;

    public IndexFinder(T[] array, int from, int to, T el) {
            this.array = array;
            this.from = from;
            this.to = to;
            this.el = el;
        }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(el)) {
                    return i;
                }
            }
            return -1;
        }
        var mid = (from + to) / 2;
        IndexFinder<T> leftFind = new IndexFinder<>(array, from, mid, el);
        IndexFinder<T> rightFind = new IndexFinder<>(array, mid + 1, to, el);
        leftFind.fork();
        rightFind.fork();
        int e1 = leftFind.join();
        int e2 = rightFind.join();
        return Math.max(e1, e2);
    }
}