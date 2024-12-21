package ch.hslu.swda.common.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Utility class for generating random numbers.
 */
public final class NumberUtil {

    /**
     * Generates a random number of specified length.
     *
     * @param length The length of the generated number.
     * @return The generated random number.
     */
    public static int generateRandomNumber(final int length) {
        String chars = "1234567890";

        StringBuilder randomText = new StringBuilder();
        Random r = new Random();

        while (randomText.length() < length) {
            int index = (int) (r.nextFloat() * chars.length());
            randomText.append(chars.charAt(index));
        }

        return Integer.parseInt(String.valueOf(randomText));
    }

    public static Double generateRandomPrice() {
        Random number = new Random();
        return number.nextDouble(100_000) / 10.0;
    }
}
