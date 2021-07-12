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
    public static final char START_CHARACTER;
    // Maximum character
    public static final char END_CHARACTER;
    // How many characters in automobile number excluding AUTOMOBILE_NUMBER_SUFFIX
    public static final int NUMBER_SIZE = 6;
    // Throw exception if trying to generate next to maximum number
    public static final String MAX_NUMBER;

    static {
        // Allowed digits: 0-9
        char[] digits = new char[10];
        for (int i = 0; i < digits.length; i++) {
            // Convert int to char
            digits[i] = (char) (i + '0');
        }

        char[] letters = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М' };
        Arrays.sort(letters);

        // Merge digits and letters
        char[] allowedCharacters = new char[digits.length + letters.length];
        System.arraycopy(digits, 0, allowedCharacters, 0, digits.length);
        System.arraycopy(letters, 0, allowedCharacters, digits.length, letters.length);
        ALLOWED_CHARACTERS = allowedCharacters;

        START_CHARACTER = allowedCharacters[0];
        END_CHARACTER = allowedCharacters[allowedCharacters.length - 1];

        String lastChar = String.valueOf(letters[letters.length - 1]);
        MAX_NUMBER = lastChar.repeat(6);
    }

    private AutomobileNumberConstants() {
        // Stop warnings about public constructor
    }
}
