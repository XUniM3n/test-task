package com.testtask.numbergenerator.service;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import com.testtask.numbergenerator.exception.MaxAutomobileNumberLetterExceeded;
import com.testtask.numbergenerator.exception.NoAutomobileNumbersInHistoryException;
import com.testtask.numbergenerator.model.AutomobileNumber;
import com.testtask.numbergenerator.repository.AutomobileNumberRepository;
import com.testtask.numbergenerator.util.AutomobileNumberUtil;
import com.testtask.numbergenerator.util.NumberRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class AutomobileNumberService {

    private static final Random random = new Random();

    private final AutomobileNumberRepository numberRepository;

    @Autowired
    public AutomobileNumberService(AutomobileNumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    public AutomobileNumber getRandomNumber() {
        String number;
        do {
            var numberChars = new char[6];
            int randomCharacterPosition = random.nextInt(AutomobileNumberConstants.ALLOWED_CHARACTERS.length);
            numberChars[1] = AutomobileNumberConstants.ALLOWED_CHARACTERS[randomCharacterPosition];

            random.nextInt(10);
            numberChars[2] = (char) (random.nextInt(10) + '0');

            random.nextInt(10);
            numberChars[3] = (char) (random.nextInt(10) + '0');

            randomCharacterPosition = random.nextInt(AutomobileNumberConstants.ALLOWED_CHARACTERS.length);
            numberChars[4] = AutomobileNumberConstants.ALLOWED_CHARACTERS[randomCharacterPosition];

            randomCharacterPosition = random.nextInt(AutomobileNumberConstants.ALLOWED_CHARACTERS.length);
            numberChars[5] = AutomobileNumberConstants.ALLOWED_CHARACTERS[randomCharacterPosition];

            number = new String(numberChars);
        } while (numberRepository.existsByNumber(number));

        var newAutomobileNumber = new AutomobileNumber(number);
        numberRepository.save(newAutomobileNumber);
        return newAutomobileNumber;
    }

    public AutomobileNumber getLastNumber() throws NoAutomobileNumbersInHistoryException {
        Optional<AutomobileNumber> lastNumber = numberRepository.findTopByOrderByIdDesc();
        if (lastNumber.isEmpty()) {
            throw new NoAutomobileNumbersInHistoryException();
        }
        return lastNumber.get();
    }

    public AutomobileNumber getNextNumber() throws NoAutomobileNumbersInHistoryException, MaxAutomobileNumberLetterExceeded {
        AutomobileNumber lastAutomobileNumber = getLastNumber();
        NumberRepresentation numberRepresentation = new NumberRepresentation(lastAutomobileNumber.getNumber());
        NumberRepresentation nextNumberRepresentation = numberRepresentation.increment();
        var newAutomobileNumber = new AutomobileNumber(nextNumberRepresentation.getNumber());
        numberRepository.save(newAutomobileNumber);
        return newAutomobileNumber;
    }
}
