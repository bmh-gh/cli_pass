package com.github.bmhgh.services.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;


class PasswordGeneratorTest {

    @Test
    void generate() {
        // Setup
        short length = (short) 128;
        String alphabetic_pw = PasswordGenerator.generate(true, true, false, false, length);
        String non_alphabetic_pw = PasswordGenerator.generate(false, false, true, true, length);
        String alphanumeric_pw = PasswordGenerator.generate(true, true, true, false, length);
        // Tests
        assertNotEquals(alphabetic_pw, "");
        assertNotEquals(non_alphabetic_pw, "");
        assertNotEquals(alphanumeric_pw, "");
        assert(alphabetic_pw.matches("[a-zA-Z]+"));
        assert(alphabetic_pw.matches("[0-9a-zA-Z]+"));
        assert(!non_alphabetic_pw.matches("[a-zA-Z]+"));
    }
}