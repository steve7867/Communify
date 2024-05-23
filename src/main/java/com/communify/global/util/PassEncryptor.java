package com.communify.global.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PassEncryptor {

    private PassEncryptor() {
    }

    public static String encrypt(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt());
    }

    public static boolean isMatch(String plain, String hashed) {
        return BCrypt.checkpw(plain, hashed);
    }
}
