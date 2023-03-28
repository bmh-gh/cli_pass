package com.github.bmhgh.services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    private static final int WORKLOAD = 12; // Number of rounds of hashing to perform

    /**
     * Hashes the given password using the bcrypt algorithm with a random salt.
     *
     * @param password the password to hash
     * @return the hashed password
     **/
    public static String hashPassword(char[] password) {
        String salt = BCrypt.gensalt(WORKLOAD);
        return BCrypt.hashpw(String.valueOf(password), salt);
    }

    /**
     * Verifies that the given password matches the given hashed password.
     *
     * @param password the password to verify
     * @param hashedPassword the hashed password to compare against
     * @return true if the password matches the hashed password, false otherwise
     */
    public static boolean checkPassword(char[] password, String hashedPassword) {
        return BCrypt.checkpw(String.valueOf(password), hashedPassword);
    }
}
