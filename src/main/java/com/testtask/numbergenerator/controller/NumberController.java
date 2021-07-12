package com.testtask.numbergenerator.controller;

import com.testtask.numbergenerator.dto.AutomobileNumberDto;
import com.testtask.numbergenerator.exception.MaxAutomobileNumberExceeded;
import com.testtask.numbergenerator.exception.NoAutomobileNumbersInHistoryException;
import com.testtask.numbergenerator.model.AutomobileNumber;
import com.testtask.numbergenerator.service.AutomobileNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class NumberController {
    private final AutomobileNumberService automobileNumberService;

    @Autowired
    public NumberController(AutomobileNumberService automobileNumberService) {
        this.automobileNumberService = automobileNumberService;
    }

    @GetMapping("/random")
    public AutomobileNumberDto random() {
        AutomobileNumber automobileNumber = automobileNumberService.getRandomNumber();
        return AutomobileNumberDto.getFromAutomobileNumber(automobileNumber);
    }

    @GetMapping("/next")
    public AutomobileNumberDto next() {
        AutomobileNumber automobileNumber;
        try {
            automobileNumber = automobileNumberService.getNextNumber();
        } catch (NoAutomobileNumbersInHistoryException | MaxAutomobileNumberExceeded e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return AutomobileNumberDto.getFromAutomobileNumber(automobileNumber);
    }
}
