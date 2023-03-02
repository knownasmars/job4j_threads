package ru.job4j.blocking;

import java.util.Random;

public class Producer implements Runnable {
    private SimpleBlockingQueue<Integer> simpleBlockingQueue;

    public Producer(SimpleBlockingQueue<Integer> simpleBlockingQueue) {
        this.simpleBlockingQueue = simpleBlockingQueue;
    }

    @Override
    public void run() {
        System.out.println("Producer started...");
        while (true) {
            try {
                simpleBlockingQueue.offer(produce());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Integer produce() {
        Integer i = new Random().nextInt(100);
        System.out.println("Producing: " + i);
        return i;
    }
}
