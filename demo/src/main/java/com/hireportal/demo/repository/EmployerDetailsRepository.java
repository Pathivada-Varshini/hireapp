package com.hireportal.demo.repository;

import com.hireportal.demo.models.EmployerDetails;
import com.hireportal.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerDetailsRepository extends JpaRepository<EmployerDetails, Long> {
    // Updated to return Optional, as it is a better practice to handle nulls
    Optional<EmployerDetails> findByUser(User user);
}