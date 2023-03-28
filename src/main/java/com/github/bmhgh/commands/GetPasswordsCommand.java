package com.github.bmhgh.commands;

import com.github.bmhgh.DefaultConfig;
import com.github.bmhgh.models.Entry;
import com.github.bmhgh.services.Persistence;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "get",
        header = "Retrieve the passwords from the storage",
        optionListHeading = "%nOptions are:%n",
        description = "")
public class GetPasswordsCommand implements Callable<Integer> {

    @Option(names = {"-f", "--file"}, description = "", defaultValue = "")
    Path path;

    @Parameters(paramLabel = "<titles>")
    List<String> titles = new ArrayList<>();

    @Override
    public Integer call() throws Exception {
        // Check if path variable is not set -> if not, take current path. The default file name is passwords
        if (path.toString().equals("")) {
            path = DefaultConfig.DEFAULT_FILE_PATH;
        }
        // Get user input for the password to decrypt the password data storage
        char[] password;

        if (System.console() == null) {
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine().toCharArray();
        } else {
            password = System.console().readPassword("Password to access the data storage: ");
        }
        Persistence persistence;
        try {
            persistence = new Persistence(path, password);
        } catch (Exception e) {
            throw new Exception(e);
        }
        List<Entry> entries = titles.isEmpty() ? persistence.getPasswords() : persistence.filterPasswords(titles);
        entries.forEach(System.out::println);
        // Error code 1 if there were no passwords
        return entries.isEmpty() ? 1 : 0;
    }
}
