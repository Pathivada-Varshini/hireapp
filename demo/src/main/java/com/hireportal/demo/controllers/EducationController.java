package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.EducationDTO;
import com.hireportal.demo.models.Education;
import com.hireportal.demo.services.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/education")
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Education> createEducation(@Valid @RequestBody EducationDTO educationDTO) {
        return ResponseEntity.ok(educationService.createEducation(educationDTO));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<List<Education>> getAllEducationDetails() {
        return ResponseEntity.ok(educationService.getAllEducationDetails());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Void> deleteEducationById(@PathVariable("id") Long educationId) {
        educationService.deleteEducationById(educationId);
        return ResponseEntity.noContent().build();
    }
}
