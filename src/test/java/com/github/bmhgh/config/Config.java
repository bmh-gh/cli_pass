package com.github.bmhgh.config;

import com.github.bmhgh.models.Entry;
import com.github.bmhgh.services.EncryptionTool;
import com.github.bmhgh.services.PasswordHasher;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Config {

    public static final Path currentPath = Paths.get(".").toAbsolutePath().normalize();
    public static final Path file = currentPath.resolve("passwords");
    public static final char[] password = "secretpassword".toCharArray();


    /**
     * @throws IOException
     * Creates a file at current Path with dummy information
     */
    public static void setup() throws IOException {
        try (FileWriter writer = new FileWriter(file.toFile())) {
            String jsonFormat = String.format("{\"verify\" : \"%s\",\"passwords\" : [\"%s\", \"%s\"] }",
                    PasswordHasher.hashPassword(password),
                    EncryptionTool.encryptData(Entry.serialize(new Entry("Github", "github.com", "123qwe")),
                            EncryptionTool.getKeyFromPassword(password)),
                    EncryptionTool.encryptData(Entry.serialize(new Entry("Google", "google.com", "1234qwer")),
                            EncryptionTool.getKeyFromPassword(password))
                    );
            writer.append(jsonFormat);
        } catch (IllegalBlockSizeException | NoSuchPaddingException | BadPaddingException | NoSuchAlgorithmException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @throws IOException
     * Deletes the created file
     */
    public static void end() throws IOException {
        Files.deleteIfExists(file);
    }

}
