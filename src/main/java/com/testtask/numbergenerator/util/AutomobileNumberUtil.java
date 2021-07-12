package com.testtask.numbergenerator.util;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import com.testtask.numbergenerator.exception.MaxAutomobileNumberLetterExceeded;

public final class AutomobileNumberUtil {

    public static boolean isCharacterInAllowedList(char character) {
        return getCharacterPositionInAllowedList(character) != -1;
    }

    private static int getCharacterPositionInAllowedList(char currentCharacter) {
        // Out of range characters are definitely not present in the list
        if (currentCharacter < AutomobileNumberConstants.START_LETTER) return -1;
        if (currentCharacter >
                AutomobileNumberConstants.ALLOWED_CHARACTERS[AutomobileNumberConstants.ALLOWED_CHARACTERS.length - 1]) {
            return -1;
        }

        // Find next character using binary search
        int start = 0;
        int end = AutomobileNumberConstants.ALLOWED_CHARACTERS.length - 1;
        int pivot = end / 2;

        // After completing loop, pivot will equal to the position of the character in ALLOWED_CHARACTERS
        while (AutomobileNumberConstants.ALLOWED_CHARACTERS[pivot] != currentCharacter && start != end) {
            if (AutomobileNumberConstants.ALLOWED_CHARACTERS[pivot] < currentCharacter) {
                start = pivot;
            } else {
                end = pivot;
            }
            pivot = (end - start) / 2 + start;

            // Workaround
            if ((end - start) == 1) {
                if (AutomobileNumberConstants.ALLOWED_CHARACTERS[start] == currentCharacter) {
                    return start;
                } else if (AutomobileNumberConstants.ALLOWED_CHARACTERS[end] == currentCharacter) {
                    return end;
                } else {
                    // Character not found
                    return -1;
                }
            }
        }

        return pivot;
    }
}
