package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class SaveFile {
    private final File file;

    public SaveFile(final File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        try (BufferedOutputStream o = new BufferedOutputStream(
                new FileOutputStream(file))) {
            for (int i = 0; i < content.length(); i += 1) {
                o.write(content.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
