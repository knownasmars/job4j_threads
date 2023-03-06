package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    void whenIncrementTwoTimesAndGetThenEqualsTwo() throws InterruptedException {
        CASCount cs = new CASCount();
        Thread first = new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                cs.increment();
                System.out.println(Thread.currentThread().getName()
                        + " increased count: " + cs.get());
            }
        });
        Thread second = new Thread(() -> {
            for (int j = 0; j < 100; j++) {
                cs.increment();
                System.out.println(Thread.currentThread().getName()
                        + " increased count: " + cs.get());
            }
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(cs.get()).isEqualTo(200);
    }
}