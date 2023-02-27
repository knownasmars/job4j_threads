package ru.job4j.concurrent;

public class Cache {
    private static Cache cache;

    public synchronized static Cache instOf() {
        return new Cache();
    }
}
