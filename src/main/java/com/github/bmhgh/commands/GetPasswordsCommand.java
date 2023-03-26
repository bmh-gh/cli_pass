package com.github.bmhgh.commands;

import com.github.bmhgh.models.Entry;
import com.github.bmhgh.services.tools.EncryptionTool;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "get",
        mixinStandardHelpOptions = true,
        header = "Retrieve the passwords from the storage",
        optionListHeading = "%nOptions are:%n",
        description = "")
public class GetPasswordsCommand implements Callable<Integer> {

    @Option(names = {"-f", "--file"}, description = "", defaultValue = "")
    Path path;

    @Parameters(paramLabel = "<titles>")
    List<String> titles = new ArrayList<>();

    @Override
    public Integer call() {

        // Check if path variable is not set -> if not, take current path. The default file name is passwords
        if (path.toString().equals("")) {
            path = Paths.get(".").toAbsolutePath().normalize().resolve("passwords");
        }
        // Get user input for the password to decrypt the password data storage
        char[] password;

        if (System.console() == null) {
            Scanner scanner = new Scanner(System.in);
            password = scanner.nextLine().toCharArray();
        } else {
            password = System.console().readPassword("Password to access the data storage: ");
        }
        List<Entry> entries;
        if (titles.isEmpty()) {
            entries = getAll(password);
        } else {
            entries = getAll(password, titles);
        }
        entries.forEach(System.out::println);

        return 0;
    }

    public List<Entry> getAll(char[] pw) {
        List<Entry> entries = new ArrayList<>();
        // Parse the Json
        try (FileReader reader = new FileReader(path.toFile())) {
            JsonArray passwords = JsonParser.parseReader(reader)
                    .getAsJsonObject()
                    .get("passwords")
                    .getAsJsonArray();
            reader.close();
            // Retrieve the Objects in the data storage as Entry Objects
            for (JsonElement current : passwords) {
                String currentAsString = current.getAsString();
                Entry currentEntry = Entry.deserialize(
                        EncryptionTool.decryptData(
                                currentAsString,
                                EncryptionTool.getKeyFromPassword(pw)
                        )
                );
                entries.add(currentEntry);
            }
        } catch (IllegalBlockSizeException | NoSuchPaddingException | IOException | BadPaddingException |
                 NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }

    public List<Entry> getAll(char[] pw, List<String> filter) {
        List<Entry> entries = new ArrayList<>();
        // Parse the Json
        try (FileReader reader = new FileReader(path.toFile())) {
            JsonArray passwords = JsonParser.parseReader(reader)
                    .getAsJsonObject()
                    .get("passwords")
                    .getAsJsonArray();
            reader.close();
            // Retrieve the Objects in the data storage as Entry Objects
            for (JsonElement current : passwords) {
                String currentAsString = current.getAsString();
                Entry currentEntry = Entry.deserialize(
                        EncryptionTool.decryptData(
                                currentAsString,
                                EncryptionTool.getKeyFromPassword(pw)
                        )
                );
                if(filter.contains(currentEntry.getTitle())) {
                    entries.add(currentEntry);
                }
            }
        } catch (IllegalBlockSizeException | NoSuchPaddingException | IOException | BadPaddingException |
                 NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return entries;
    }
}
