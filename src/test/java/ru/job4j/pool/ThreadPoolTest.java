package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import ru.job4j.CASCount;

import static org.assertj.core.api.Assertions.*;

class ThreadPoolTest {
    @Test
    public void whenStartPoolWithTasks() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        CASCount casCount = new CASCount();
        threadPool.work(casCount::increment);
        threadPool.work(casCount::increment);
        threadPool.work(casCount::increment);
        threadPool.shutdown();
        assertThat(casCount.get()).isEqualTo(3);
    }
}