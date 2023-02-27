package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis() / 1000;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                fileOutputStream.flush();
                try {
                    long end = System.currentTimeMillis() / 1000;
                    long rsl = end - start;
                    long time = (speed / dataBuffer.length * 1000);
                    if ((rsl - end) < time) {
                        Thread.sleep(time - rsl);
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void validate(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("некорректные данные");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis() / 1000;
        validate(args);
        String url = args[0];
        String fileName = args[2];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed, fileName));
        wget.start();
        wget.join();
        long finish = System.currentTimeMillis() / 1000;
        System.out.println("программа работала секунд: " + (finish - start));

    }
}