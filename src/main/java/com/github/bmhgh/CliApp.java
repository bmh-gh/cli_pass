package com.github.bmhgh;

import com.github.bmhgh.commands.AddPasswordCommand;
import com.github.bmhgh.commands.CreateFileCommand;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "clipwm",
        version = "clipwm dev 1.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        header = "Password manager CLI",
        optionListHeading = "%nOptions are:%n",
        commandListHeading = "%nSubcommands are:%n",
        subcommands = {
                CreateFileCommand.class,
                AddPasswordCommand.class,
        },
        description = "This is a simple password manager app for the command line"
)
public class CliApp implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliApp()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call()  {
        return 0;
    }
}
