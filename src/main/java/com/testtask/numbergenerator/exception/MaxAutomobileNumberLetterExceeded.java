package com.testtask.numbergenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Max letters exceeded")
public class MaxAutomobileNumberLetterExceeded extends Exception{
    @Override
    public String getMessage() {
        return "Max letters exceeded";
    }
}
