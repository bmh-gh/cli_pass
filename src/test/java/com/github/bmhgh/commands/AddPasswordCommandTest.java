package com.github.bmhgh.commands;

import com.github.bmhgh.CliApp;
import com.github.bmhgh.config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AddPasswordCommandTest {

    private final char[] password = Config.password;
    CommandLine cli = new CommandLine(new CliApp());

    @BeforeEach
    void setup() throws IOException {
        Config.setup();
    }

    @AfterEach
    void end() throws IOException {
        Config.end();
    }

    @Test
    void addPassword() throws IOException {
        // Create input to simulate
        String simulatedInput = new String(password) +  "\nMyTitle\nMyUrl\nMyPassword\n";

        // Set the input stream to a ByteArrayInputStream with the simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String[] args = {"add", "--file=passwords"};
        int exitCode = cli.execute(args);

        String content = Files.readString(Paths.get("passwords"));
        assertNotNull(content);
        assertEquals(0, exitCode);
    }
}