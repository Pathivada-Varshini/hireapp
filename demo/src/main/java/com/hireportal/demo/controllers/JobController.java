package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.JobDTO;
import com.hireportal.demo.enums.ExperienceRequired;
import com.hireportal.demo.enums.JobType;
import com.hireportal.demo.enums.SalaryRange;
import com.hireportal.demo.models.Job;
import com.hireportal.demo.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/create")
    public ResponseEntity<JobDTO> createJob(
            @RequestParam("userId") Long userId,
            @RequestParam("categoryId") Long categoryId,
            @Valid @RequestBody JobDTO jobDTO) {
        Job job = jobService.createJob(userId, categoryId, convertToEntity(jobDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(job));
    }

    @PatchMapping("/{jobId}")
    public ResponseEntity<JobDTO> partialUpdateJob(
            @PathVariable("jobId") Long jobId,
            @Valid @RequestBody JobDTO jobDTO) {
        Job updatedJob = jobService.partialUpdateJob(jobId, jobDTO);
        return ResponseEntity.ok(convertToDTO(updatedJob));
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable("jobId") Long jobId) {
        Job job = jobService.getJobById(jobId);
        return ResponseEntity.ok(convertToDTO(job));
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        List<JobDTO> jobDTOs = jobs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jobDTOs);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobDTO> updateJob(
            @PathVariable("jobId") Long jobId,
            @Valid @RequestBody JobDTO jobDTO) {
        Job updatedJob = jobService.updateJob(jobId, convertToEntity(jobDTO));
        return ResponseEntity.ok(convertToDTO(updatedJob));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterJobs(
            @RequestParam(value = "experienceRequired", required = false) ExperienceRequired experienceRequired,
            @RequestParam(value = "salaryRange", required = false) SalaryRange salaryRange,
            @RequestParam(value = "jobType", required = false) JobType jobType) {

        List<Job> filteredJobs = jobService.filterJobs(experienceRequired, salaryRange, jobType);

        if (filteredJobs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No jobs found matching the specified criteria.");
        }
        List<JobDTO> jobDTOs = filteredJobs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jobDTOs);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable("jobId") Long jobId) {
        jobService.deleteJob(jobId);
        return ResponseEntity.noContent().build();
    }

    private JobDTO convertToDTO(Job job) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setJobId(job.getJobId());
        jobDTO.setJobTitle(job.getJobTitle());
        jobDTO.setCompanyName(job.getCompanyName());
        jobDTO.setJobDescription(job.getJobDescription());
        jobDTO.setSkillsRequired(job.getSkillsRequired());
        jobDTO.setCategoryId(job.getCategory().getCategoryId());
        jobDTO.setJobType(job.getJobType());
        jobDTO.setSalaryRange(job.getSalaryRange());
        jobDTO.setExperienceRequired(job.getExperienceRequired());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setUserId(job.getUser() != null ? job.getUser().getUserId() : null);
        jobDTO.setPostDate(job.getPostDate());
        jobDTO.setEndDate(job.getEndDate());
        return jobDTO;
    }

    private Job convertToEntity(JobDTO jobDTO) {
        Job job = new Job();
        job.setJobId(jobDTO.getJobId());
        job.setJobTitle(jobDTO.getJobTitle());
        job.setCompanyName(jobDTO.getCompanyName());
        job.setJobDescription(jobDTO.getJobDescription());
        job.setSkillsRequired(jobDTO.getSkillsRequired());
        job.setJobType(jobDTO.getJobType());
        job.setSalaryRange(jobDTO.getSalaryRange());
        job.setExperienceRequired(jobDTO.getExperienceRequired());
        job.setLocation(jobDTO.getLocation());
        job.setPostDate(jobDTO.getPostDate());
        job.setEndDate(jobDTO.getEndDate());
        return job;
    }
}
