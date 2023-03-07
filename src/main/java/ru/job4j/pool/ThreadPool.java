package ru.job4j.pool;

import ru.job4j.blocking.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() throws InterruptedException {
        int size = Runtime.getRuntime().availableProcessors();
        tasks = new SimpleBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        try {
                            tasks.poll().run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    })
            );
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        threads.forEach(thread -> {
            Thread.currentThread().interrupt();
        });
    }
}