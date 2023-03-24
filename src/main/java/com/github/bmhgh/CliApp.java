package com.github.bmhgh;

import com.github.bmhgh.commands.PasswordManager;
import picocli.CommandLine;


public class CliApp {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new PasswordManager()).execute(args);
        System.exit(exitCode);
    }
}
