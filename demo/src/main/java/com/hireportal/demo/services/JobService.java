package com.hireportal.demo.services;

import com.hireportal.demo.exceptions.NotFoundException;
import com.hireportal.demo.models.Category;
import com.hireportal.demo.models.Job;
import com.hireportal.demo.models.User;
import com.hireportal.demo.repository.CategoryRepository;
import com.hireportal.demo.repository.JobRepository;
import com.hireportal.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public JobService(JobRepository jobRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // Create a new job with the specified userId and categoryId
    @Transactional
    public Job createJob(Long userId, Long categoryId, Job job) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        job.setUser(user);
        job.setCategory(category);

        return jobRepository.save(job);
    }

    // Get a job by ID
    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found"));
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Apply to a job (JOB_SEEKER)
    @Transactional
    public void applyToJob(Long jobId, Long userId) {
        Job job = getJobById(jobId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Log the application process or store in a separate table if needed.
        System.out.println("User " + user.getUserId() + " applied to job " + job.getJobId());
    }

    // Update an existing job
    @Transactional
    public Job updateJob(Long jobId, Job updatedJob) {
        Job existingJob = getJobById(jobId);

        existingJob.setJobTitle(updatedJob.getJobTitle());
        existingJob.setCompanyName(updatedJob.getCompanyName());
        existingJob.setJobDescription(updatedJob.getJobDescription());
        existingJob.setSkillsRequired(updatedJob.getSkillsRequired());
        existingJob.setJobType(updatedJob.getJobType());
        existingJob.setSalaryRange(updatedJob.getSalaryRange());
        existingJob.setExperienceRequired(updatedJob.getExperienceRequired());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setPostDate(updatedJob.getPostDate());
        existingJob.setEndDate(updatedJob.getEndDate());

        return jobRepository.save(existingJob);
    }

    // Delete a job by its ID
    @Transactional
    public void deleteJob(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new NotFoundException("Job not found");
        }
        jobRepository.deleteById(jobId);
    }
}
