package com.hireportal.demo.dto;

import com.hireportal.demo.enums.ExperienceRequired;
import com.hireportal.demo.enums.JobType;
import com.hireportal.demo.enums.SalaryRange;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class JobDTO {

    private Long jobId;

    @NotBlank(message = "Job title cannot be blank")
    private String jobTitle;

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;

    @NotBlank(message = "Job description cannot be blank")
    private String jobDescription;

    @NotBlank(message = "Skills required cannot be blank")
    private String skillsRequired;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Job type is required")
    private JobType jobType;

    @NotNull(message = "Salary range is required")
    private SalaryRange salaryRange;

    @NotNull(message = "Experience required is required")
    private ExperienceRequired experienceRequired;

    @NotBlank(message = "Location cannot be blank")
    private String location;

    private Long userId;

    @NotNull(message = "Post date is required")
    private Date postDate;

    @NotNull(message = "End date is required")
    private Date endDate;
}