package ru.job4j.fork;

import java.util.concurrent.ForkJoinPool;
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

    public static <T> Integer finder(T[] array, T el) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(
                new IndexFinder<>(array, 0, array.length - 1, el));
    }

    private int linear() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(el)) {
                rsl = i;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linear();
        }
        int  mid = (from + to) / 2;
        IndexFinder<T> leftFind =
                new IndexFinder<>(array, from, mid, el);
        IndexFinder<T> rightFind =
                new IndexFinder<>(array, mid + 1, to, el);
        leftFind.fork();
        rightFind.fork();
        int leftElement = leftFind.join();
        int rightElement = rightFind.join();
        return Math.max(leftElement, rightElement);
    }
}