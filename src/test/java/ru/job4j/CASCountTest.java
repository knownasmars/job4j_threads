package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    void whenIncrementTwoTimesAndGetThenEqualsTwo() throws InterruptedException {
        CASCount cs = new CASCount();
        Thread first = new Thread(() -> {
            cs.increment();
            int i = 0;
            while (i++ < 100) {
                cs.increment();
                System.out.println(Thread.currentThread().getName() + " increased count: " + cs.get());
            }
        });
        Thread second = new Thread(() -> {
            int i = 0;
            while (i++ < 100) {
                cs.increment();
                System.out.println(Thread.currentThread().getName() + " increased count: " + cs.get());
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cs.get()).isEqualTo(201);
    }
}