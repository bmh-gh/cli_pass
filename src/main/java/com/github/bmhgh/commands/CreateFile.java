package com.github.bmhgh.commands;

import com.github.bmhgh.services.StorageService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "new",
        mixinStandardHelpOptions = true,
        requiredOptionMarker = '*',
        header = "Create a new storage for passwords",
        optionListHeading = "%nOptions are:%n",
        commandListHeading = "%nSubcommands are:%n",
        //defaultValueProvider = ,
        description = "This is a simple password manager app for the command line")
public class CreateFile implements Callable<Integer> {

    @Option(names = {"-p", "--password"}, description = "", defaultValue = "", interactive = true)
    private char[] password;

    @Option(names = {"--path"}, description = "", defaultValue = "")
    private Path path;

    @Parameters(paramLabel = "<filename>", defaultValue = "passwords")
    private String filename;

    @Override
    public Integer call() throws Exception {
        // Check if path variable is not set -> if not, take current path
        if (path.toString().equals("")) {
            path = Paths.get(".").toAbsolutePath().normalize();
        }
        // add the filename to the current path
        path = path.resolve(filename);
        // create a new file. if it already exists, print it to the console.
        boolean is_created = StorageService.createFile(path);
        if (is_created) {
            System.out.println("File is successfully created");
        }
        else {
            System.out.println("The file already exists");
        }
        return is_created ? 0 : 1; // exit code
    }
}
