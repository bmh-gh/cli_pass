package com.bmhgh;
import java.io.IOException;
import java.nio.file.*;

public class Manager {

    public static void create_json(String path, String name) throws IOException {
        Path p = Path.of(path, name);
        Files.createFile(p);
    }

    public static void delete_json(String path) throws IOException {
        Files.deleteIfExists(Path.of(path));
    }
}
