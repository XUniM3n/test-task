package com.testtask.numbergenerator;

import com.testtask.numbergenerator.exception.MaxAutomobileNumberLetterExceeded;
import com.testtask.numbergenerator.util.NumberRepresentation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class NumberUtilUnitTests {
    @Test
    void testGetNextNumber() {
        try {
            assertEquals("C400BA", new NumberRepresentation("C399BA").increment().getNumber());
            assertEquals("С000ВВ", new NumberRepresentation("С999ВА").increment().getNumber());
            assertEquals("B089XM", new NumberRepresentation("B088XM").increment().getNumber());
        } catch (MaxAutomobileNumberLetterExceeded maxAutomobileNumberLetterExceeded) {
            // Exception is not expected
            fail();
        }
    }
}
