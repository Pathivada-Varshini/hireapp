package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.EmployerDetailsDTO;
import com.hireportal.demo.models.EmployerDetails;
import com.hireportal.demo.services.EmployerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employers")
public class EmployerDetailsController {

    private final EmployerDetailsService employerDetailsService;

    @Autowired
    public EmployerDetailsController(EmployerDetailsService employerDetailsService) {
        this.employerDetailsService = employerDetailsService;
    }

    // Only accessible by JOB_PROVIDER
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @GetMapping("/{userId}")
    public ResponseEntity<EmployerDetails> getEmployerDetailsByUserId(@PathVariable("userId") Long userId) {
        try {
            EmployerDetails employerDetails = employerDetailsService.getEmployerDetailsByUserId(userId);
            return ResponseEntity.ok(employerDetails);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Only accessible by JOB_PROVIDER
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @PutMapping("/{userId}")
    public ResponseEntity<EmployerDetails> updateEmployerDetails(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody EmployerDetailsDTO updatedDetailsDTO) {
        EmployerDetails updatedEmployerDetails = employerDetailsService.updateEmployerDetails(userId, updatedDetailsDTO);
        return ResponseEntity.ok(updatedEmployerDetails);
    }
}
