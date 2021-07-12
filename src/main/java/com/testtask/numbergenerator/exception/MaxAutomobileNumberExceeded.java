package com.testtask.numbergenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MaxAutomobileNumberExceeded extends Exception{
    @Override
    public String getMessage() {
        return "Max automobile number exceeded";
    }
}
