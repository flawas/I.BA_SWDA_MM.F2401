package ch.hslu.swda.util;

import java.util.Random;

public final class StringUtil {
    public static String generateRandomText(final int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder randomText = new StringBuilder();
        Random r = new Random();

        while (randomText.length() < length) {
            int index = (int) (r.nextFloat() * chars.length());
            randomText.append(chars.charAt(index));
        }

        return randomText.toString();
    }
}
