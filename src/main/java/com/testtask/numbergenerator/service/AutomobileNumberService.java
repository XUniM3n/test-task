package com.testtask.numbergenerator.service;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import com.testtask.numbergenerator.exception.MaxAutomobileNumberExceeded;
import com.testtask.numbergenerator.exception.NoAutomobileNumbersInHistoryException;
import com.testtask.numbergenerator.model.AutomobileNumber;
import com.testtask.numbergenerator.repository.AutomobileNumberRepository;
import com.testtask.numbergenerator.util.AutomobileNumberUtil;
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
            var numberChars = new char[AutomobileNumberConstants.NUMBER_SIZE];
            for (var i = 0; i < AutomobileNumberConstants.NUMBER_SIZE; i++) {
                int randomCharacterPosition = random.nextInt(AutomobileNumberConstants.ALLOWED_CHARACTERS.length);
                numberChars[i] = AutomobileNumberConstants.ALLOWED_CHARACTERS[randomCharacterPosition];
            }
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

    public AutomobileNumber getNextNumber() throws NoAutomobileNumbersInHistoryException, MaxAutomobileNumberExceeded {
        AutomobileNumber lastAutomobileNumber = getLastNumber();
        String newNumber = AutomobileNumberUtil.getNextNumber(lastAutomobileNumber.getNumber());
        var newAutomobileNumber = new AutomobileNumber(newNumber);
        numberRepository.save(newAutomobileNumber);
        return newAutomobileNumber;
    }
}
