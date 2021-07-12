package com.testtask.numbergenerator.dto;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import com.testtask.numbergenerator.model.AutomobileNumber;
import lombok.Getter;

@Getter
public class AutomobileNumberDto {

    private final String number;

    public AutomobileNumberDto(String number) {
        this.number = number;
    }

    public static AutomobileNumberDto getFromAutomobileNumber(AutomobileNumber automobileNumber) {
        String numberWithSuffix = String.format("%s %s", automobileNumber.getNumber(),
                AutomobileNumberConstants.AUTOMOBILE_NUMBER_SUFFIX);
        return new AutomobileNumberDto(numberWithSuffix);
    }
}