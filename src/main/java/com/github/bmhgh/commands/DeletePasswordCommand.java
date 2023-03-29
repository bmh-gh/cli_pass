package com.github.bmhgh.commands;

import com.github.bmhgh.DefaultConfig;
import com.github.bmhgh.exceptions.FalsePasswordException;
import com.github.bmhgh.services.Persistence;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "del",
        aliases = "delete",
        header = "Deletes the password with the given title from the storage",
        optionListHeading = "%nOptions are:%n",
        description = "")
public class DeletePasswordCommand implements Callable<Integer> {

    @Option(names = {"-f", "--files"}, description = "Path to the file")
    private final Path path = DefaultConfig.DEFAULT_FILE_PATH;

    @Option(names = {"-a", "--all"}, description = "Delete all files with the title")
    boolean all;

    @CommandLine.Parameters(paramLabel = "<title>", description = "List of the titles of all entries that shall be deleted")
    List<String> titles = new ArrayList<>();

    @Override
    public Integer call() {

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

        if(all) {
            try {
                persistence.deleteAll(titles);
            } catch (IOException e) {
                System.out.println("Could not delete this entry/these entries");
                return 1;
            }
        } else {
            // todo(): Maybe not all titles are supposed to be deleted
            System.out.println("cannot do this yet");
        }

        return  0;
    }
}
