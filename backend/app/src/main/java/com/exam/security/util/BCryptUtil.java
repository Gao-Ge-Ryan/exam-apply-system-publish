package com.exam.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptUtil {
    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encode(String str) {
        String encode = passwordEncoder.encode(str);
        return encode;
    }
}
