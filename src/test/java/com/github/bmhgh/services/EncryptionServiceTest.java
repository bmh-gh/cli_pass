package com.github.bmhgh.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EncryptionServiceTest {

    Path path = Paths.get("").toAbsolutePath().normalize()
            .resolve("src\\test\\java\\com\\github\\bmhgh\\config\\testConfig");

    @BeforeEach
    public void setup() throws IOException {
        Files.createFile(path);
        try (FileWriter writer = new FileWriter(path.toFile())) {
            writer.write("""
                    {"header" : "some header information","passwords" : [{"test" : "test"}]}
                    """);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @AfterEach
    public void end() throws IOException {
        Files.deleteIfExists(path);
    }

    @Test
    void encrypt_decryptFile() throws IOException {
        boolean success = EncryptionService.encryptFile(path, "123qwe".toCharArray());
        // the encryption was successful
        assert success;
        success = EncryptionService.decryptFile(path, "123qwe".toCharArray());
        // the decryption was successful
        String should = """
                    {"header" : "some header information","passwords" : [{"test" : "test"}]}
                    """.replaceAll("\\s", "").replaceAll("\\n", "");
        String is = Files.readString(path).replaceAll("\\s", "").replaceAll("\\n", "");
        assertEquals(is, should);
        assert success;
    }
}