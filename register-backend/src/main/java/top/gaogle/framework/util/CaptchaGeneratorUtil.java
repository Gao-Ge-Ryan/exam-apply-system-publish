package top.gaogle.framework.util;

import java.security.SecureRandom;

public class CaptchaGeneratorUtil {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CAPTCHA_LENGTH = 6;

    public static String generateCaptcha() {
        StringBuilder captcha = new StringBuilder(CAPTCHA_LENGTH);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            captcha.append(CHARACTERS.charAt(index));
        }
        return captcha.toString();
    }

}
