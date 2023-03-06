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
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        isNull(account);
        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("id не может быть равен \"0\"");
        }
        return accounts.remove(id, accounts.get(id));
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (amount == 0) {
            throw new IllegalArgumentException("Сумма не может быть равна \"0\"");
        }
        if (fromId <= 0 || toId <= 0) {
            throw new IllegalArgumentException("Счет не может быть отрицательным!");
        }
        boolean rsl = false;
        Optional<Account> optFrom = getById(fromId);
        Optional<Account> optTo = getById(toId);
        if (optFrom.isPresent() && optTo.isPresent()
                && optTo.get().amount() >= amount) {
            Account accounFrom = optFrom.get();
            Account accountTo = optTo.get();
            int accFromTotalAmount = accounFrom.amount() - amount;
            int accToTotalAmount = accountTo.amount() + amount;
            accounts.put(fromId, new Account(fromId, accFromTotalAmount));
            accounts.put(toId, new Account(toId, accToTotalAmount));
            rsl = true;
        }
        return rsl;
    }
}