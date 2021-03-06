package com.testtask.numbergenerator.controller;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import com.testtask.numbergenerator.exception.MaxAutomobileNumberLetterExceeded;
import com.testtask.numbergenerator.exception.NoAutomobileNumbersInHistoryException;
import com.testtask.numbergenerator.model.AutomobileNumber;
import com.testtask.numbergenerator.service.AutomobileNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberController {
    private final AutomobileNumberService automobileNumberService;

    @Autowired
    public NumberController(AutomobileNumberService automobileNumberService) {
        this.automobileNumberService = automobileNumberService;
    }

    @GetMapping("/random")
    public String random() {
        AutomobileNumber automobileNumber = automobileNumberService.getRandomNumber();
        return String.format("%s %s",automobileNumber.getNumber(), AutomobileNumberConstants.AUTOMOBILE_NUMBER_SUFFIX);
    }

    @GetMapping("/next")
    public String next() {
        AutomobileNumber automobileNumber;
        try {
            automobileNumber = automobileNumberService.getNextNumber();
        } catch (NoAutomobileNumbersInHistoryException | MaxAutomobileNumberLetterExceeded e) {
            return "Error: " + e.getMessage();
        }
        return String.format("%s %s",automobileNumber.getNumber(), AutomobileNumberConstants.AUTOMOBILE_NUMBER_SUFFIX);
    }
}
