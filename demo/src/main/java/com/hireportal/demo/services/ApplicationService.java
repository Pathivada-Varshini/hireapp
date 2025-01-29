package com.hireportal.demo.services;

import com.hireportal.demo.dto.ApplicationDTO;
import com.hireportal.demo.models.Application;
import com.hireportal.demo.models.Job;
import com.hireportal.demo.models.User;
import com.hireportal.demo.repository.ApplicationRepository;
import com.hireportal.demo.repository.JobRepository;
import com.hireportal.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }

    // Create Application using ApplicationDTO
    public Application createApplication(Long userId, Long jobId, ApplicationDTO applicationDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Job> jobOptional = jobRepository.findById(jobId);

        if (userOptional.isEmpty() || jobOptional.isEmpty()) {
            throw new IllegalArgumentException("User or Job not found");
        }

        Application application = new Application();
        application.setStatus(applicationDTO.getStatus());
        application.setUser(userOptional.get());
        application.setJob(jobOptional.get());

        return applicationRepository.save(application);
    }

    // Get all Applications
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    // Get Applications by User ID
    public List<Application> getApplicationsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        return applicationRepository.findByUser(userOptional.get());
    }

    // Get Applications by Job ID
    public List<Application> getApplicationsByJobId(Long jobId) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isEmpty()) {
            throw new IllegalArgumentException("Job not found");
        }
        return applicationRepository.findByJob(jobOptional.get());
    }

    // Get Application by ID
    public Optional<Application> getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId);
    }

    // Update Application using ApplicationDTO
    public Application updateApplication(Long applicationId, ApplicationDTO applicationDTO) {
        Application existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        existingApplication.setStatus(applicationDTO.getStatus());

        return applicationRepository.save(existingApplication);
    }

    // Partially update Application
    public Application partiallyUpdateApplication(Long applicationId, ApplicationDTO applicationDTO) {
        Application existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        if (applicationDTO.getStatus() != null) {
            existingApplication.setStatus(applicationDTO.getStatus());
        }

        return applicationRepository.save(existingApplication);
    }

    // Delete Application by ID
    public void deleteApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }
}
