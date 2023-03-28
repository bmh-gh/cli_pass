package com.github.bmhgh.commands;

import com.github.bmhgh.models.Entry;
import com.github.bmhgh.services.Persistence;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "add",
        header = "Add a new password to the storage",
        optionListHeading = "%nOptions are:%n",
        description = "")
public class AddPasswordCommand implements Callable<Integer> {

    @Option(names = {"-f", "--file"}, defaultValue = "")
    private Path path;

    @Parameters(paramLabel = "<title>", index = "0", description = "Title for the new entry", interactive = true)
    String entryTitle;
    @Parameters(paramLabel = "<url>", index = "0", description = "URL to the website for the new entry", interactive = true)
    String entryUrl;
    @Parameters(paramLabel = "<password>", index = "0", description = "Password for the new entry", interactive = true)
    String entryPassword;

    @Override
    public Integer call() {
        // Check if path variable is not set -> if not, take current path. The default file name is passwords
        if (path.toString().equals("")) {
            path = Paths.get(".").toAbsolutePath().normalize().resolve("passwords");
        }

        char[] password;

        if (System.console() == null) {
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine().toCharArray();
            entryTitle = scanner.nextLine();
            entryUrl = scanner.nextLine();
            entryPassword = scanner.nextLine();
        } else {
            password = System.console().readPassword("Password to access the data storage: ");
            if (entryTitle == null) {
                entryTitle = System.console().readLine("Title: ");
            }
            if (entryUrl == null) {
                entryUrl = System.console().readLine("URL: ");
            }
            if (entryPassword == null) {
                entryPassword = Arrays.toString(System.console().readPassword("Password: "));
            }
        }

        Entry newEntry = new Entry(entryTitle, entryUrl, entryPassword);
        try {
            Persistence persistence = new Persistence(path, password);
            persistence.addPassword(newEntry);
        } catch (Exception e) {
            // there was an error:
            return 1;
        }
        // everything went smoothly:
        return 0;
    }
}
