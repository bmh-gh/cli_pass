package com.github.bmhgh.commands;

import com.github.bmhgh.services.StorageService;
import com.github.bmhgh.services.PasswordHasher;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "new",
        mixinStandardHelpOptions = true,
        header = "Create a new storage for passwords",
        optionListHeading = "%nOptions are:%n",
        description = "This is a simple password manager app for the command line")
public class CreateFileCommand implements Callable<Integer> {

    @Option(names = {"-p", "--password"}, description = "", defaultValue = "", interactive = true)
    private char[] password;

    @Option(names = {"--path"}, description = "", defaultValue = "")
    private Path path;

    @Parameters(paramLabel = "<filename>", defaultValue = "passwords")
    private String filename;

    @Override
    public Integer call() {
        // Check if path variable is not set -> if not, take current path
        if (path.toString().equals("")) {
            path = Paths.get(".").toAbsolutePath().normalize();
        }
        // add the filename to the current path
        path = path.resolve(filename);
        // create a new file. if it already exists, print it to the console.
        boolean is_created = StorageService.createFile(path, PasswordHasher.hashPassword(password));
        if (is_created) {
            System.out.println("File is successfully created");
            return 0;
        }
        else {
            System.out.println("The file already exists");
            return 1;
        }
    }
}
