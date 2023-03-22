package com.github.bmhgh;

import java.util.Random;

public class PasswordGenerator {
    public static String generate(boolean upper, boolean lower, boolean num, boolean special, short length) {
        StringBuilder sb = new StringBuilder();
        if (upper) {
            sb.append(getUpperCase());
        }
        if (lower) {
            sb.append(getLowerCase());
        }
        if (num) {
            sb.append(getNumerical());
        }
        if (special) {
            sb.append(getSpecial());
        }
        char[] set = sb.toString().toCharArray();
        // Maximal password length is 128, so every other number will be adjusted
        length = (short) (length & 128);
        Random rng = new Random();
        StringBuilder pw = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int rand = rng.nextInt(set.length);
            pw.append(set[rand]);
        }
        return pw.toString();
    }
    private static String getLowerCase() {
        return "abcdefghijklmnopqrstuvwxyz";
    }

    private static String getUpperCase() {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

    private static String getNumerical() {
        return "1234567890";
    }

    private static String getSpecial() {
        return "!@#$%^&*()-_=+[{]};:,<.>/?\\|`~\"";
    }
}

