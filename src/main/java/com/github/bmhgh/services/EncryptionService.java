package com.github.bmhgh.services;

import com.github.bmhgh.services.tools.EncryptionTool;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;

public class EncryptionService {


    public static boolean encryptFile(Path path, char[] password) {
        // files are in json format so the json can simply be parsed with gson and be encrypted
        // if there is no field called "passwords", the File cannot be parsed
        try {
            // body information starts with the keyword "passwords" in a json Array:
            FileReader reader = new FileReader(path.toFile());
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
            // Get the field with the key "passwords".
            // If this throws an exception (key "passwords" doesn't exist) the return value of this function is false
            String passwords = obj.getAsJsonArray("passwords").toString();
            obj.remove("passwords");

            // Encrypt String and attach to json with key "passwords_encrypted":
            String cipher = EncryptionTool.encryptData(passwords, EncryptionTool.getKeyFromPassword(password));
            obj.addProperty("passwords_encrypted", cipher);


            // write it to the file:
            try (FileWriter writer = new FileWriter(path.toFile())) {
                writer.write(obj.toString());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean decryptFile(Path path, char[] password) {
        // files are in json format so the json can simply be parsed with gson and be encrypted
        // if there is no field called "passwords", the File cannot be parsed
        try {
            // body information starts with the keyword "passwords" in a json Array:
            FileReader reader = new FileReader(path.toFile());
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
            // Get the field with the key "passwords_encrypted".
            // If this throws an exception (key "passwords_encrypted" doesn't exist) the return value of this function is false
            String passwords = obj.get("passwords_encrypted").getAsString();
            obj.remove("passwords_encrypted");

            // Encrypt String and attach to json with key "passwords_encrypted":
            String plain = EncryptionTool.decryptData(passwords, EncryptionTool.getKeyFromPassword(password));
            JsonArray jsonPlain = JsonParser.parseString(plain).getAsJsonArray();
            obj.add("passwords", jsonPlain);


            // write it to the file:
            try (FileWriter writer = new FileWriter(path.toFile())) {
                writer.write(obj.toString());
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
            //return false;
        }
    }
}
