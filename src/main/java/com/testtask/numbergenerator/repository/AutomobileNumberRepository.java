package com.testtask.numbergenerator.repository;

import com.testtask.numbergenerator.model.AutomobileNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutomobileNumberRepository extends JpaRepository<AutomobileNumber, Long> {
    // Get last automobile number
    Optional<AutomobileNumber> findTopByOrderByIdDesc();

    boolean existsByNumber(String number);
}
