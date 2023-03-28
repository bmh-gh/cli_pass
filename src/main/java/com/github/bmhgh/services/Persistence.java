package com.github.bmhgh.services;

import com.github.bmhgh.models.Entry;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Persistence {

    private final Path path;
    private final char[] password;

    JsonObject fileContent;

    public Persistence(Path path, char[] password) throws Exception {
        this.path = path;
        fileContent = updateFileContent();
        this.password = validatePassword(password);
    }

    private char[] validatePassword(char[] password) throws Exception {
        if (!PasswordHasher.checkPassword(password, fileContent.get("verify").getAsString())) {
            throw new Exception("This password doesn't match!");
        }
        return password;
    }

    private JsonObject updateFileContent() throws IOException {
        try (FileReader reader = new FileReader(path.toFile())) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public List<Entry> getPasswords() throws IOException {
        updateFileContent();
        if (fileContent.get("passwords").getAsJsonArray().isEmpty()) {
            return Collections.emptyList();
        }
        return fileContent.get("passwords")
                .getAsJsonArray().asList().stream()
                .map(jsonElement -> {
                    try {
                        return Entry.deserialize(
                                EncryptionTool.decryptData(
                                        jsonElement.getAsString(),
                                        EncryptionTool.getKeyFromPassword(password)
                                )
                        );
                    } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                             NoSuchPaddingException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    public List<Entry> setPasswords(List<Entry> entries) throws IOException {
        updateFileContent();
        JsonArray newPasswords = new JsonArray();
        entries.stream().map(entry -> {
            try {
                return EncryptionTool.encryptData(
                        Entry.serialize(entry),
                        EncryptionTool.getKeyFromPassword(password)
                );
            } catch (IOException | IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException |
                     NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }).forEach(newPasswords::add);
        fileContent.remove("passwords");
        fileContent.add("passwords", newPasswords);
        write();
        return getPasswords();
    }

    public void addPassword(Entry entry)
            throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException,
            BadPaddingException, InvalidKeyException {
        updateFileContent();
        fileContent.get("passwords")
                .getAsJsonArray()
                .add(
                        EncryptionTool.encryptData(
                                Entry.serialize(entry),
                                EncryptionTool.getKeyFromPassword(password)
                        )
                );
        write();
    }

    private void write() throws IOException {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            writer.write(fileContent.toString());
        } catch (IOException e) {
            // TODO: Custom exception mb??
            throw new IOException(e);
        }
        updateFileContent();
    }

    public List<Entry> filterPasswords(List<String> filter) throws IOException {
        updateFileContent();
        return getPasswords().stream()
                .filter(entry -> filter.contains(entry.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Entry> deleteAll(List<String> titles) throws IOException {
        updateFileContent();
        return setPasswords(getPasswords().stream().
                filter(entry -> !titles.contains(entry.getTitle()))
                .toList());
    }
}
