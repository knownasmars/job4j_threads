package ru.job4j.blocking;

public class Consumer implements Runnable {
    private final SimpleBlockingQueue<Integer> simpleBlockingQueue;

    public Consumer(SimpleBlockingQueue<Integer> simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        System.out.println("Consumer started...");
        consume();
    }

    private void consume() {
        System.out.println("Consuming: " + simpleBlockingQueue.poll());
    }
}
