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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('JOB_PROVIDER', 'JOB_SEEKER')")
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyAuthority('JOB_PROVIDER', 'JOB_SEEKER')")
    public ResponseEntity<List<Application>> getApplicationsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUserId(userId));
    }

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasRole('JOB_PROVIDER')")
    public ResponseEntity<List<Application>> getApplicationsByJobId(@PathVariable("jobId") Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJobId(jobId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    public ResponseEntity<Application> getApplicationById(@PathVariable("id") Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found"));
        return ResponseEntity.ok(application);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable("id") Long applicationId,
            @RequestParam("status") ApplicationStatus status) {
        Application updatedApplication = applicationService.updateApplicationStatus(applicationId, status);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") Long applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}