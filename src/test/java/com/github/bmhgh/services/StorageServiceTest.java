package com.github.bmhgh.services;

import com.github.bmhgh.CliApp;
import com.github.bmhgh.services.tools.PasswordHashingTool;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StorageServiceTest {

    @AfterEach
    void end() throws IOException {
        Files.deleteIfExists(Paths.get("testfile"));
    }

    @Test
    void createFile() throws Exception {
        // Check verification
        // Set up test data
        String[] args = {"new", "--password=mypassword", "testfile"};

        // Run the command
        int exitCode = new CommandLine(new CliApp()).execute(args);

        // Parse the password hash
        FileReader reader = new FileReader(Paths.get("testfile").toFile());
        String pwHash = JsonParser.parseReader(reader).getAsJsonObject().get("verify").getAsString();
        reader.close();

        assertEquals(0, exitCode);
        assert PasswordHashingTool.checkPassword("mypassword".toCharArray(), pwHash);
    }
}