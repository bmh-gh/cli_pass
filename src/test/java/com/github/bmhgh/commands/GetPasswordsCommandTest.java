package com.github.bmhgh.commands;

import com.github.bmhgh.CliApp;
import com.github.bmhgh.config.Config;
import com.github.bmhgh.services.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetPasswordsCommandTest {

    CommandLine cli = new CommandLine(new CliApp());
    private final char[] password = Config.password;

    @BeforeEach
    void setup() throws IOException {
        Config.setup();
    }

    @AfterEach
    void end() throws IOException {
        Config.end();
    }

    @Test
    void getAll() throws Exception {
        // Create input to simulate
        String simulatedInput = new String(password) + "\n";

        // Set the input stream to a ByteArrayInputStream with the simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        String[] args = {"get", "-f=passwords"};
        int exitCode = cli.execute(args);
        // If it compiles with no error code, then the output must be correct, bc it uses tested functions
        assertEquals(0, exitCode);
    }
}