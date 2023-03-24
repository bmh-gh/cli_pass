package com.github.bmhgh.services.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHashingToolTest {

    @Test
    void hashPassword() {
        String password = "1234qwer";
        String hash = PasswordHashingTool.hashPassword(password.toCharArray());
        assertNotEquals(password, hash);
    }

    @Test
    void checkPassword() {
        char[] password = "1234qwer".toCharArray();
        String hash = PasswordHashingTool.hashPassword(password);

        assert PasswordHashingTool.checkPassword(password, hash);
    }
}