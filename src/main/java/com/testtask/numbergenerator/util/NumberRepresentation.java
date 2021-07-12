package com.testtask.numbergenerator.util;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import com.testtask.numbergenerator.exception.MaxAutomobileNumberLetterExceeded;

public class NumberRepresentation {
    private String lettersString;
    private String digitsString;

    public NumberRepresentation(String number) {
        this.lettersString = number.charAt(0) + number.substring(4, 6);
        this.digitsString = number.substring(1, 4);
    }

    public String getNumber() {
        return lettersString.charAt(0) + digitsString + lettersString.charAt(1) + lettersString.charAt(2);
    }

    /*
    Return itself
     */
    public NumberRepresentation increment() throws MaxAutomobileNumberLetterExceeded {
        char[] letters = lettersString.toCharArray();
        short digits = Short.parseShort(digitsString);
        digits++;

        if (digits == 1000) {
            // Increment letters part
            for (int i = 2; i >= 0; i--) {
                // If non-highest bit
                if (i != 0) {
                    // If character has not reached it's maximum
                    if (letters[i] != AutomobileNumberConstants.END_LETTER) {
                        // Increment character
                        letters[i] = getNextCharacter(letters[i]);
                        // If characters hasn't reached maximum continue loop
                        if (letters[i] != AutomobileNumberConstants.END_LETTER) break;
                    } else {
                        // If character reached maximum set it to lowest character
                        letters[i] = AutomobileNumberConstants.START_LETTER;
                    }
                    // If highest bit
                } else if (i == 0) {
                    // If maximum number exceeded
                    if (letters[0] == AutomobileNumberConstants.END_LETTER)
                        throw new MaxAutomobileNumberLetterExceeded();
                    // Increment character
                    letters[i] = getNextCharacter(letters[i]);
                    break;
                }
            }

            this.digitsString = "000";
            this.lettersString = new String(letters);
        } else {
            String digitsString = String.valueOf(digits);
            if (digitsString.length() == 1) {
                digitsString = "00" + digitsString;
            } else if (digitsString.length() == 2) {
                digitsString = "0" + digitsString;
            }
            this.digitsString = digitsString;
        }

        return this;
    }

    private static boolean isCharacterInAllowedList(char character) {
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

    private static char getNextCharacter(char currentCharacter) {
        int pivot = getCharacterPositionInAllowedList(currentCharacter);

        return AutomobileNumberConstants.ALLOWED_CHARACTERS[pivot + 1];
    }
}
