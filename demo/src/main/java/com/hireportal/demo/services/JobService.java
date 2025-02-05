package com.hireportal.demo.services;

import com.hireportal.demo.dto.JobDTO;
import com.hireportal.demo.enums.ExperienceRequired;
import com.hireportal.demo.enums.JobType;
import com.hireportal.demo.enums.Role;
import com.hireportal.demo.enums.SalaryRange;
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
    @Transactional
    public Job createJob(Long userId, Long categoryId, Job job) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        job.setUser(user);
        job.setCategory(category);


        if (user.getRole() == Role.JOB_PROVIDER && user.getEmployerDetails() != null) {
            job.setCompanyName(user.getEmployerDetails().getCompanyName());
        }

        return jobRepository.save(job);
    }

    @Transactional
    public Job partialUpdateJob(Long jobId, JobDTO jobDTO) {
        Job existingJob = getJobById(jobId);

        if (jobDTO.getJobTitle() != null) {
            existingJob.setJobTitle(jobDTO.getJobTitle());
        }
        if (jobDTO.getCompanyName() != null) {
            existingJob.setCompanyName(jobDTO.getCompanyName());
        }
        if (jobDTO.getJobDescription() != null) {
            existingJob.setJobDescription(jobDTO.getJobDescription());
        }
        if (jobDTO.getSkillsRequired() != null) {
            existingJob.setSkillsRequired(jobDTO.getSkillsRequired());
        }
        if (jobDTO.getJobType() != null) {
            existingJob.setJobType(jobDTO.getJobType());
        }
        if (jobDTO.getSalaryRange() != null) {
            existingJob.setSalaryRange(jobDTO.getSalaryRange());
        }
        if (jobDTO.getExperienceRequired() != null) {
            existingJob.setExperienceRequired(jobDTO.getExperienceRequired());
        }
        if (jobDTO.getLocation() != null) {
            existingJob.setLocation(jobDTO.getLocation());
        }
        if (jobDTO.getPostDate() != null) {
            existingJob.setPostDate(jobDTO.getPostDate());
        }
        if (jobDTO.getEndDate() != null) {
            existingJob.setEndDate(jobDTO.getEndDate());
        }

        return jobRepository.save(existingJob);
    }
    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found"));
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

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
    public List<Job> filterJobs(ExperienceRequired experienceRequired, SalaryRange salaryRange, JobType jobType) {
        return jobRepository.findFilteredJobs(experienceRequired, salaryRange, jobType);
    }

    @Transactional
    public void deleteJob(Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new NotFoundException("Job not found");
        }
        jobRepository.deleteById(jobId);
    }
}