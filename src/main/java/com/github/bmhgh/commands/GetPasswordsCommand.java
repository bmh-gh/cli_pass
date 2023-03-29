package com.github.bmhgh.commands;

import com.github.bmhgh.DefaultConfig;
import com.github.bmhgh.exceptions.FalsePasswordException;
import com.github.bmhgh.models.Entry;
import com.github.bmhgh.services.Persistence;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
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
        } catch (IOException e) {
            System.out.println("Not a compatible file! Please select another one");
            return 1;
        } catch(FalsePasswordException e) {
            System.out.println("The password does not match!");
            return 2;
        }
        List<Entry> entries = titles.isEmpty() ? persistence.getPasswords() : persistence.filterPasswords(titles);
        entries.forEach(System.out::println);
        // Error code 1 if there were no entries
        return entries.isEmpty() ? 1 : 0;
    }
}
