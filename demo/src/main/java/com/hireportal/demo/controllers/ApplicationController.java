package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.ApplicationDTO;
import com.hireportal.demo.enums.ApplicationStatus;
import com.hireportal.demo.exceptions.NotFoundException;
import com.hireportal.demo.models.Application;
import com.hireportal.demo.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // Apply to a Job - Available for JOB_SEEKER only
    @PostMapping("/{jobId}/apply")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<String> applyToJob(@PathVariable("jobId") Long jobId, @RequestParam("userId") Long userId) {
        try {
            applicationService.applyToJob(userId, jobId);
            return ResponseEntity.ok("Application submitted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User or Job not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Get all Applications - Accessible for both JOB_PROVIDER and JOB_SEEKER
    @GetMapping
    @PreAuthorize("hasAnyAuthority('JOB_PROVIDER', 'JOB_SEEKER')")
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    // Get Applications by User ID - Accessible for JOB_PROVIDER and JOB_SEEKER
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('JOB_PROVIDER', 'JOB_SEEKER')")
    public ResponseEntity<List<Application>> getApplicationsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUserId(userId));
    }

    // Get Applications by Job ID - Accessible for JOB_PROVIDER only
    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasRole('JOB_PROVIDER')")
    public ResponseEntity<List<Application>> getApplicationsByJobId(@PathVariable("jobId") Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJobId(jobId));
    }

    // Get a specific Application by ID - Accessible for both JOB_PROVIDER and JOB_SEEKER
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('JOB_PROVIDER', 'JOB_SEEKER')")
    public ResponseEntity<Application> getApplicationById(@PathVariable("id") Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found"));
        return ResponseEntity.ok(application);
    }

    // Update Application Status - Accessible for JOB_PROVIDER only
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable("id") Long applicationId,
            @RequestParam("status") ApplicationStatus status) {
        Application updatedApplication = applicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok(updatedApplication);
    }

    // Delete an Application - Accessible for JOB_PROVIDER only
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") Long applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}
