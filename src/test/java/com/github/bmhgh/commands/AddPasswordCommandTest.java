package com.github.bmhgh.commands;

import com.github.bmhgh.CliApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class AddPasswordCommandTest {

    CommandLine cli = new CommandLine(new CliApp());

    @BeforeEach
    void setup() {
        String[] args = {"new", "--password=mypassword", "testfile"};
        cli.execute(args);
    }

    @AfterEach
    void end() throws IOException {
        Files.deleteIfExists(Paths.get("testfile"));
    }

    @Test
    void addPassword() throws IOException {
        // Create input to simulate
        String simulatedInput = "secretpassword\nMyTitle\nMyUrl\nMyPassword\n";

        // Set the input stream to a ByteArrayInputStream with the simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String[] args = {"add", "--file=testfile"};
        int exitCode = cli.execute(args);
        String content = Files.readString(Paths.get("testfile"));
        assertNotNull(content);
        assertEquals(0, exitCode);
    }
}