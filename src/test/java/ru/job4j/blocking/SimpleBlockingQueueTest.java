package ru.job4j.blocking;

import org.junit.jupiter.api.Test;

public class SimpleBlockingQueueTest {
    @Test
    void whenOfferThenPoll() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producing = new Thread(
                () -> {
                    Producer producer = new Producer(queue);
                    producer.run();
                });
        Thread consuming = new Thread(
                () -> {
                    Consumer consumer = new Consumer(queue);
                    consumer.run();
                });
        producing.start();
        Thread.sleep(1000);
        consuming.start();
    }
}