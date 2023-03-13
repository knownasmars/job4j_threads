package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public void emailTo(User user) {
        pool.submit(
                () -> {
                    String subject = String.format(
                        "Notification %s, to this email %s",
                        user.getUserName(),
                        user.getEmail()
                    );
                    String body = String.format(
                            "Add a new event to %s",
                            user.getUserName()
                    );
                    send(subject, body, user.getEmail());
                }
        );
        close();
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }
}