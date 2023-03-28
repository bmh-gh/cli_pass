package com.github.bmhgh.commands;

import com.github.bmhgh.CliApp;
import com.github.bmhgh.config.Config;
import com.github.bmhgh.models.Entry;
import com.github.bmhgh.services.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeletePasswordCommandTest {

    private final char[] password = Config.password;

    @BeforeEach
    void setup() throws IOException {
        Config.setup();
    }

    @AfterEach
    void end() throws Exception {
        Config.end();
    }

    @Test
    void call() throws Exception {
        // Create input to simulate
        String simulatedInput = new String(password) + "\n";

        // Set the input stream to a ByteArrayInputStream with the simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        String[] args = {"del", "-a", "Google"};
        int exitCode = new CommandLine(new CliApp()).execute(args);

        assert exitCode == 0;
        Persistence persistence = new Persistence(Config.file, password);
        assertEquals(
                List.of(new Entry("Github", "github.com", "123qwe")).toString(),
                persistence.getPasswords().toString()
        );

        // Set the input stream to a ByteArrayInputStream with the simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        args = new String[]{"del", "-a", "Github"};
        exitCode = new CommandLine(new CliApp()).execute(args);

        // ???? Why this call? Why doesn't it work without it? I update the file content every single time before I access it
        persistence = new Persistence(Config.file, password);

        assert exitCode == 0;
        assert persistence.getPasswords().isEmpty();
    }
}