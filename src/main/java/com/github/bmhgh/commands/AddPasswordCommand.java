package com.github.bmhgh.commands;

import com.github.bmhgh.exceptions.FalsePasswordException;
import com.github.bmhgh.models.Entry;
import com.github.bmhgh.services.Persistence;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "add",
        mixinStandardHelpOptions = true,
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
        // Get user input. If there is no console (like in an IDE) -> use the scanner. Else use the console:
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
                entryPassword = new String(System.console().readPassword("Password: "));
            }
        }
        // Set up the connection to the data storage and add the entry. If there was a mistake it returns an error code.
        // Otherwise, it returns 0 (successful)
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
        Entry newEntry = new Entry(entryTitle, entryUrl, entryPassword);
        try {
            persistence.addPassword(newEntry);
        } catch (IOException e) {
            System.out.println("Not a compatible file! Please select another one");
            return 1;
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException |
                BadPaddingException | InvalidKeyException e) {
            System.out.println("Failure while trying to encrypt the input");
            return 2;
        }
        // everything went smoothly:
        return 0;
    }
}
