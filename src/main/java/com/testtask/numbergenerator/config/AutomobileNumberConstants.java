package com.testtask.numbergenerator.config;

import java.util.Arrays;

public final class AutomobileNumberConstants {
    public static final String REGION = "116";
    public static final String COUNTRY = "RUS";
    // Russian automobile number's suffix
    public static final String AUTOMOBILE_NUMBER_SUFFIX = String.format("%s %s", REGION, COUNTRY);
    // Not all characters of the alphabet are being used for automobile numbers. Here is the list of allowed characters.
    public static final char[] ALLOWED_CHARACTERS;
    // Minimum character
    public static final char START_LETTER;
    // Maximum character
    public static final char END_LETTER;

    static {
        char[] letters = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М' };
        Arrays.sort(letters);

        ALLOWED_CHARACTERS = letters;

        START_LETTER = letters[0];
        END_LETTER = letters[letters.length - 1];
    }

    private AutomobileNumberConstants() {
        // Stop warnings about public constructor
    }
}
