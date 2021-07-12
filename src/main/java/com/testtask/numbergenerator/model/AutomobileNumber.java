package com.testtask.numbergenerator.model;

import com.testtask.numbergenerator.config.AutomobileNumberConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "automobile_number_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AutomobileNumber {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, length = AutomobileNumberConstants.NUMBER_SIZE, nullable = false)
    private String number;

    public AutomobileNumber(String number) {
        this.number = number;
    }
}
