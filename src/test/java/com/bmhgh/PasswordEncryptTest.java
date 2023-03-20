package com.bmhgh;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class PasswordEncryptTest {

    @Test
    void encrypt_password() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String pw = "simple_password";
        String master_pw = "1234qwer";
        PasswordEncrypt passwordEncrypt = new PasswordEncrypt(master_pw);
        String cipherText = passwordEncrypt.encrypt_password(pw);
        String plainText = passwordEncrypt.decrypt_password(cipherText);
        Assertions.assertEquals(pw, plainText);
    }
}