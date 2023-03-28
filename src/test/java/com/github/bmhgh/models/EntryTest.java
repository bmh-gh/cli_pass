package com.github.bmhgh.models;

import com.github.bmhgh.services.EncryptionTool;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntryTest {

    @Test
    void bidirectional() throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, NoSuchPaddingException, BadPaddingException, InvalidKeyException {
        char[] password = "secretpassword".toCharArray();
        Entry originalEntry = new Entry("Github", "bmh_github", "123qwe");
        String serialized_enc = EncryptionTool.encryptData(
                Entry.serialize(originalEntry), EncryptionTool.getKeyFromPassword(password)
        );
        Entry newEntry = Entry.deserialize(EncryptionTool.decryptData(
                serialized_enc, EncryptionTool.getKeyFromPassword(password)
                )
        );
        assert newEntry != null;
        assertEquals(originalEntry.toString(),newEntry.toString());
    }
}