package com.testtask.numbergenerator.util;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import com.testtask.numbergenerator.exception.MaxAutomobileNumberExceeded;

public final class AutomobileNumberUtil {
    public static String getNextNumber(String oldNumber) throws MaxAutomobileNumberExceeded {
        char[] number = oldNumber.toCharArray();
        for (int i = AutomobileNumberConstants.NUMBER_SIZE - 1; i >= 0; i--) {
            // If non-highest bit
            if (i != 0) {
                // If character has not reached it's maximum
                if (number[i] != AutomobileNumberConstants.END_CHARACTER) {
                    // Increment character
                    number[i] = getNextCharacter(number[i]);
                    // If characters hasn't reached maximum continue loop
                    if (number[i] != AutomobileNumberConstants.END_CHARACTER) break;
                } else {
                    // If character reached maximum set it to lowest character
                    number[i] = AutomobileNumberConstants.START_CHARACTER;
                }
                // If highest bit
            } else if (i == 0) {
                // If maximum number exceeded
                if (number[0] == AutomobileNumberConstants.END_CHARACTER) throw new MaxAutomobileNumberExceeded();
                // Increment character
                number[i] = getNextCharacter(number[i]);
                break;
            }
        }

        return new String(number);
    }

    public static boolean isCharacterInAllowedList(char character) {
        return getCharacterPositionInAllowedList(character) != -1;
    }

    private static int getCharacterPositionInAllowedList(char currentCharacter) {
        // Out of range characters are definitely not present in the list
        if (currentCharacter < AutomobileNumberConstants.ALLOWED_CHARACTERS[0]) return -1;
        if (currentCharacter >
                AutomobileNumberConstants.ALLOWED_CHARACTERS[AutomobileNumberConstants.ALLOWED_CHARACTERS.length - 1]) {
            return -1;
        }

        // Find next character using binary search
        int start = 0;
        int end = AutomobileNumberConstants.ALLOWED_CHARACTERS.length - 1;
        int pivot = end / 2;
        // After completing loop pivot will be equals to the position of the character in ALLOWED_CHARACTERS
        while (AutomobileNumberConstants.ALLOWED_CHARACTERS[pivot] != currentCharacter && start != end) {
            if (AutomobileNumberConstants.ALLOWED_CHARACTERS[pivot] < currentCharacter) {
                start = pivot;
            } else {
                end = pivot;
            }
            pivot = (int) Math.ceil(((end - start) / (double) 2)) + start;
        }

        // Character is not in the allowed list
        if (start == end) return -1;

        return pivot;
    }


    private static char getNextCharacter(char currentCharacter) {
        int pivot = getCharacterPositionInAllowedList(currentCharacter);

        return AutomobileNumberConstants.ALLOWED_CHARACTERS[pivot + 1];
    }
}
