package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/'};
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (char c : process) {
                    System.out.print("\r load: " + c);
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.currentThread().interrupt();
    }
}
