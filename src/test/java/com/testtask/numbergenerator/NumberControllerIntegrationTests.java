package com.testtask.numbergenerator;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import com.testtask.numbergenerator.exception.MaxAutomobileNumberLetterExceeded;
import com.testtask.numbergenerator.util.AutomobileNumberUtil;
import com.testtask.numbergenerator.util.NumberRepresentation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.ServletContext;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NumberControllerIntegrationTests {
    @Autowired
    private ServletContext servletContext;

    @LocalServerPort
    private int port;

    private static final Pattern pattern = Pattern.compile(String.format("[А-Я][0-9]{3}[А-Я]{2} %s %s",
            AutomobileNumberConstants.REGION, AutomobileNumberConstants.COUNTRY));

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    void testRandomNumber() {
        ResponseEntity<String> response = restTemplate.
                getForEntity(createURLWithPort("/random"), String.class);

        checkResponseForValidity(response);
    }

    @Test
    void testNextNumber() {
        ResponseEntity<String> response = restTemplate.
                getForEntity(createURLWithPort("/random"), String.class);

        checkResponseForValidity(response);
        String oldNumber = response.getBody();
        NumberRepresentation numberRepresentation = new NumberRepresentation(oldNumber);

        String predictedNewNumber = null;
        try {
            predictedNewNumber = numberRepresentation.increment().getNumber();
        } catch (MaxAutomobileNumberLetterExceeded maxAutomobileNumberLetterExceeded) {
            // If this exception has been thrown it means letter part exceeded maximum possible value
            String maximumNumber = AutomobileNumberConstants.END_LETTER + "999" + AutomobileNumberConstants.END_LETTER +
                    AutomobileNumberConstants.END_LETTER;
            assertEquals(maximumNumber, oldNumber);
        }

        response = restTemplate.getForEntity(createURLWithPort("/next"), String.class);
        assertTrue(response.hasBody());

        String responseBody = response.getBody();
        // If oldNumber is maximum number
        if (predictedNewNumber == null) {
            assertEquals("Error: Max letters exceeded", responseBody);
        } else {
            checkResponseForValidity(response);
        }

        assertEquals(responseBody, predictedNewNumber);
    }

    private void checkResponseForValidity(ResponseEntity<String> response) {
        String responseBody = response.getBody();
        assertNotNull(responseBody);

        // Check that string contains correct amount of characters and ends with " 116 RUS"
        assertTrue(pattern.matcher(responseBody).find());

        char[] number = responseBody.substring(0, 6).toCharArray();

        // Check that every letter is in allowed list
        assertTrue(AutomobileNumberUtil.isCharacterInAllowedList(number[0]));
        assertTrue(AutomobileNumberUtil.isCharacterInAllowedList(number[4]));
        assertTrue(AutomobileNumberUtil.isCharacterInAllowedList(number[5]));
    }

    private String createURLWithPort(String uri) {
        return String.format("http://127.0.0.1:%s/%s%s", port, servletContext.getContextPath(), uri);
    }
}
