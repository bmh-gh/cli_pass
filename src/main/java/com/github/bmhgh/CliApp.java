package com.github.bmhgh;

import com.github.bmhgh.commands.CreateFile;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "clipwm",
        version = "clipwm dev 1.0",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        header = "Password manager CLI",
        optionListHeading = "%nOptions are:%n",
        commandListHeading = "%nSubcommands are:%n",
        subcommands = {
                CreateFile.class
                //add more subcommands later here
        },
        description = "This is a simple password manager app for the command line"
)
public class CliApp implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CliApp()).execute("new", "-p");
        System.exit(exitCode);
    }

    @Override
    public Integer call()  {
        return 0;
    }
}
