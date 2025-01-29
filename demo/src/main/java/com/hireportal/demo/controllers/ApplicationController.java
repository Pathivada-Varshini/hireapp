package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.ApplicationDTO;
import com.hireportal.demo.exceptions.NotFoundException;
import com.hireportal.demo.models.Application;
import com.hireportal.demo.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Job Seeker can create an application
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<Application> createApplication(
            @RequestParam("userId") Long userId,
            @RequestParam("jobId") Long jobId,
            @Valid @RequestBody ApplicationDTO applicationDTO) {

        return ResponseEntity.ok(applicationService.createApplication(userId, jobId, applicationDTO));
    }

    // Get all applications - accessible by both JOB_SEEKER and JOB_PROVIDER
    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    // Get applications by userId
    @PreAuthorize("hasAuthority('JOB_SEEKER') and #userId == authentication.principal.id")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getApplicationsByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUserId(userId));
    }

    // Get applications by jobId
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<Application>> getApplicationsByJobId(@PathVariable("jobId") Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJobId(jobId));
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable("id") Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId)
                .orElseThrow(() -> new NotFoundException("Application not found"));
        return ResponseEntity.ok(application);
    }


    // Update application
    @PreAuthorize("hasAuthority('JOB_SEEKER') and #applicationId == authentication.principal.id")
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable("id") Long applicationId, @Valid @RequestBody ApplicationDTO applicationDTO) {
        return ResponseEntity.ok(applicationService.updateApplication(applicationId, applicationDTO));
    }

    // Delete application
    @PreAuthorize("hasAuthority('JOB_SEEKER') and #applicationId == authentication.principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable("id") Long applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }
}
