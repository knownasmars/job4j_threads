package ru.job4j.blocking;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public Queue<T> getQueue() {
        return queue;
    }

    public synchronized void offer(T value) {
        System.out.println("Trying to put");
        while (queue.size() >= limit) {
            try {
                wait();
                Thread.yield();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (queue.size() == 0) {
            System.out.println("Queue is empty, notify");
            notifyAll();
        }
        queue.add(value);
        System.out.println(value + " added");
    }

    public synchronized T poll() {
        System.out.println("Trying to take out");
        while (queue.size() == 0) {
            System.out.println("Queue is empty, wait");
            try {
                wait();
                Thread.yield();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (queue.size() == limit) {
            System.out.println("Queue is full, notify");
            notifyAll();
        }
        return queue.remove();
    }
}