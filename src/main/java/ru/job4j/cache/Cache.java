package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        return memory.computeIfPresent(model.getId(), (id, base) -> {
            if (stored.getVersion() != base.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(id, base.getVersion() + 1, model.getName());
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}