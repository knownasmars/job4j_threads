package ru.job4j.pool;

import ru.job4j.CASCount;
import ru.job4j.blocking.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        tasks = new SimpleBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(
                    () -> {
                        while (!Thread.currentThread().isInterrupted()) {
                            try {
                                tasks.poll().run();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                e.printStackTrace();
                            }
                        }
                    })
            );
        }
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(thread -> {
            Thread.currentThread().interrupt();
        });
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        CASCount casCount = new CASCount();
        for (int i = 0; i < 10; i++) {
            threadPool.work(casCount::increment);
        }
        System.out.println(casCount.get());
        threadPool.shutdown();
    }
}