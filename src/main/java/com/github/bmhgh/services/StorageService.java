package com.github.bmhgh.services;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class StorageService {

    public static boolean createFile(Path path, String password_hash) {
        if (Files.exists(path)) {
            return false;
        }
        try {
            // Later add header information which do not get encrypted
            // Add header information here:
            try (FileWriter writer = new FileWriter(path.toFile())) {
                String jsonFormat = String.format("{\n \"verify\" : \"%s\",\n\"passwords\" : [\n\n] }", password_hash);
                writer.append(jsonFormat);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
