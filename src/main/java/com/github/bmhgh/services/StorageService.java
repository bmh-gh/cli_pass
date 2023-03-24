package com.github.bmhgh.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StorageService {

    public static boolean createFile(Path path) throws IOException {
        if(Files.exists(path)) {
            return false;
        }
        try {
            Files.createFile(path);
            // Add header information:
            return false;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
