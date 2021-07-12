package com.testtask.numbergenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
If history table is empty this exception is thrown
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No automobile numbers in history to complete this operation")
public class NoAutomobileNumbersInHistoryException extends Exception{
    @Override
    public String getMessage() {
        return "No automobile numbers in history to complete this operation";
    }
}
