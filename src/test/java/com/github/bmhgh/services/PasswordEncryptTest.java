package com.github.bmhgh.services.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class PasswordEncryptTest {

    @Test
    void encrypt_password()
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String pw = "simple_password";
        char[] master_pw = "12345qwerz".toCharArray();
        String cipherText = EncryptionTool.encryptData(pw, EncryptionTool.getKeyFromPassword(master_pw));
        String plainText = EncryptionTool.decryptData(cipherText, EncryptionTool.getKeyFromPassword(master_pw));
        Assertions.assertEquals(pw, plainText);
    }
}