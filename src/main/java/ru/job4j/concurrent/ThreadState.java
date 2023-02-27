package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
            Thread.currentThread().interrupt();
        }
        System.out.println("работа завершена");
    }
}