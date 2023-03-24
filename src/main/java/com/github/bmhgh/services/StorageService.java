package com.github.bmhgh.services;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StorageService {

    public static boolean createFile(Path path) throws IOException {
        if (Files.exists(path)) {
            return false;
        }
        try {
            // Later add header information which do not get encrypted
            // Add header information here:
            try (FileWriter writer = new FileWriter(path.toFile())) {
                String jsonFormat = "{ \"passwords\" : [\n\n], }";
                writer.append(jsonFormat);
            }
            return true;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
