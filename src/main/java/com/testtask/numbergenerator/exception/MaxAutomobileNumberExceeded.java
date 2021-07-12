package com.testtask.numbergenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Max automobile number exceeded")
public class MaxAutomobileNumberExceeded extends Exception{
    @Override
    public String getMessage() {
        return "Max automobile number exceeded";
    }
}
