package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {
    private final File file;

    public ParseFile(final File file) {
        this.file = file;
    }

    public String content(Predicate<Character> filter) {
        int data;
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(
                new FileInputStream(file))) {
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public Predicate<Character> filterWithoutUnicode() {
        return x -> x < 0x80;
    }
}