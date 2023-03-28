package com.github.bmhgh.services;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionTool {
    private static final String algorithm = "AES/ECB/PKCS5Padding";

    public static SecretKey getKeyFromPassword(char[] password) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] hash = sha.digest(new String(password).getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(hash, "AES");
    }
    public static String encryptData(String plain, SecretKey k) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.ENCRYPT_MODE, k);
        return Base64.getEncoder().encodeToString(c.doFinal(plain.getBytes()));
    }

    public static String decryptData(String cipher, SecretKey k) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher c = Cipher.getInstance(algorithm);
        c.init(Cipher.DECRYPT_MODE, k);
        return new String(c.doFinal(Base64.getDecoder().decode(cipher)));
    }
}
