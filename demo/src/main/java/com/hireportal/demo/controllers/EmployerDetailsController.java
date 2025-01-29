package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.EmployerDetailsDTO;
import com.hireportal.demo.models.EmployerDetails;
import com.hireportal.demo.services.EmployerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{userId}")
    public ResponseEntity<EmployerDetails> getEmployerDetailsByUserId(@PathVariable("userId") Long userId) {
        EmployerDetails employerDetails = employerDetailsService.getEmployerDetailsByUserId(userId);
        return ResponseEntity.ok(employerDetails);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<EmployerDetails> updateEmployerDetails(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody EmployerDetailsDTO updatedDetailsDTO) {
        EmployerDetails updatedEmployerDetails = employerDetailsService.updateEmployerDetails(userId, updatedDetailsDTO);
        return ResponseEntity.ok(updatedEmployerDetails);
    }
}