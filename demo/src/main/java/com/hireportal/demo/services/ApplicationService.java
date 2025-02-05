package com.hireportal.demo.services;

import com.hireportal.demo.dto.ApplicationDTO;
import com.hireportal.demo.enums.ApplicationStatus;
import com.hireportal.demo.models.Application;
import com.hireportal.demo.models.Job;
import com.hireportal.demo.models.User;
import com.hireportal.demo.repository.ApplicationRepository;
import com.hireportal.demo.repository.JobRepository;
import com.hireportal.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    public Application applyToJob(Long userId, Long jobId) {
        try {
            Optional<User> userOptional = userRepository.findById(userId);
            Optional<Job> jobOptional = jobRepository.findById(jobId);

            if (userOptional.isEmpty() || jobOptional.isEmpty()) {
                throw new IllegalArgumentException("User or Job not found");
            }

            Application application = new Application();
            application.setUser(userOptional.get());
            application.setJob(jobOptional.get());
            application.setStatus(ApplicationStatus.PENDING);

            return applicationRepository.save(application);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An unexpected error occurred while applying to the job.");
        }
    }

    public Application updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        Application existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        existingApplication.setStatus(status);
        return applicationRepository.save(existingApplication);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public List<Application> getApplicationsByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        return applicationRepository.findByUser(userOptional.get());
    }

    public List<Application> getApplicationsByJobId(Long jobId) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isEmpty()) {
            throw new IllegalArgumentException("Job not found");
        }
        return applicationRepository.findByJob(jobOptional.get());
    }

    public Optional<Application> getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId);
    }

    public Application updateApplication(Long applicationId, ApplicationDTO applicationDTO) {
        Application existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        existingApplication.setStatus(applicationDTO.getStatus());

        return applicationRepository.save(existingApplication);
    }

    public Application partiallyUpdateApplication(Long applicationId, ApplicationDTO applicationDTO) {
        Application existingApplication = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found"));

        if (applicationDTO.getStatus() != null) {
            existingApplication.setStatus(applicationDTO.getStatus());
        }

        return applicationRepository.save(existingApplication);
    }

    public void deleteApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }
}