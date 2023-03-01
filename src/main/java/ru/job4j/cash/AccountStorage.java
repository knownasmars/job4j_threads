package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public static void isNull(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Выберите значение");
        }
    }

    public synchronized boolean add(Account account) {
        isNull(account);
        boolean rsl = false;
        HashMap<Integer, Account> shallowCopy = new HashMap<>(accounts);
        accounts.put(account.id(), account);
        if (shallowCopy.size() < accounts.size()
                && accounts.get(account.id()).amount() == account.amount()) {
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        isNull(account);
        boolean rsl = false;
        HashMap<Integer, Account> shallowCopy = new HashMap<>(accounts);
        accounts.put(account.id(), account);
        if (accounts.get(account.id()) != shallowCopy.get(account.id())) {
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("id не может быть равен \"0\"");
        }
        boolean rsl = false;
        HashMap<Integer, Account> shallowCopy = new HashMap<>(accounts);
        accounts.remove(id);
        if (!accounts.containsKey(id)) {
            rsl = true;
        }
        return rsl;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("Сумма не может быть равна \"0\"");
        }
        boolean rsl = false;
        if (getById(fromId).isPresent() && getById(toId).isPresent()) {
            Account accFrom = accounts.get(fromId);
            Account accTo = accounts.get(toId);
            int accFromTotalAmount = accFrom.amount() - amount;
            int accToTotalAmount = accTo.amount() + amount;
            accounts.put(fromId, new Account(fromId, accFromTotalAmount));
            accounts.put(toId, new Account(toId, accToTotalAmount));
            rsl = true;
        }
        return rsl;
    }
}