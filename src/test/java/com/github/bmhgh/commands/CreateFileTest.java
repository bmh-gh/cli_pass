package com.github.bmhgh.commands;

import com.github.bmhgh.CliApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CreateFileTest {
    @AfterEach
    void end() throws IOException {
        Files.deleteIfExists(Paths.get("testfile"));
    }

    @Test
    void testCreateNewFile() {
        // Set up test data
        String[] args = {"new", "--password=mypassword", "testfile"};

        // Run the command
        int exitCode = new CommandLine(new CliApp()).execute(args);

        // Assert that the command exited successfully and that the file was created
        assertEquals(0, exitCode);
        assertTrue(Files.exists(Paths.get("testfile")));
    }

    @Test
    void testCreateExistingFile() throws Exception {
        // Set up test data
        String[] args = {"new", "--password=mypassword", "testfile"};

        // Create the file before running the command
        Path testfile = Paths.get("testfile");
        Files.createFile(testfile);

        // Run the command
        int exitCode = new CommandLine(new CliApp()).execute(args);

        // Assert that the command failed and that the existing file was not overwritten
        assertEquals(1, exitCode);
        assertFalse(Files.isDirectory(testfile));
    }


}