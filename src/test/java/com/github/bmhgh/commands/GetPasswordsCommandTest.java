package com.github.bmhgh.commands;

import com.github.bmhgh.CliApp;
import com.github.bmhgh.models.Entry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetPasswordsCommandTest {

    CommandLine cli = new CommandLine(new CliApp());

    @BeforeEach
    void setup() {
        String[] args = new String[] {"new", "--password=secretpassword", "testfile"};
        cli.execute(args);
        // Create input to simulate
        String simulatedInput = "secretpassword\nMyTitle\nMyUrl\nMyPassword\n";

        // Set the input stream to a ByteArrayInputStream with the simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        args = new String[]{"add", "--file=testfile"};
        cli.execute(args);

        // Create input to simulate
        simulatedInput = "secretpassword\nOtherTitle\nOtherUrl\nOtherPassword\n";

        // Set the input stream to a ByteArrayInputStream with the simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        args = new String[]{"add", "--file=testfile"};
        cli.execute(args);
    }

    @AfterEach
    void end() throws IOException {
        Files.deleteIfExists(Paths.get("testfile"));
    }

    @Test
    void getAll() {
        // Create input to simulate
        String simulatedInput = "secretpassword\n";

        // Set the input stream to a ByteArrayInputStream with the simulated input
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        String[] args = {"get", "-f=testfile"};
        int exitCode = cli.execute(args);

        assertEquals(0, exitCode);
    }

    @Test
    void password() {
        GetPasswordsCommand get = new GetPasswordsCommand();
        get.path = Paths.get("testfile");
        List<Entry> out = get.getAll("secretpassword".toCharArray(), List.of("OtherTitle"));
        List<Entry> should = List.of(
                new Entry("OtherTitle", "OtherUrl", "OtherPassword")
        );
        assertEquals(should.toString(), out.toString());
    }
}