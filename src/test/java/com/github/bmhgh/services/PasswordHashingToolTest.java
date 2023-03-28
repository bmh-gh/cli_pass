package com.github.bmhgh.services;

import com.github.bmhgh.services.PasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHashingToolTest {

    @Test
    void hashPassword() {
        String password = "1234qwer";
        String hash = PasswordHasher.hashPassword(password.toCharArray());
        assertNotEquals(password, hash);
    }

    @Test
    void checkPassword() {
        char[] password = "1234qwer".toCharArray();
        String hash = PasswordHasher.hashPassword(password);

        assert PasswordHasher.checkPassword(password, hash);
    }
}