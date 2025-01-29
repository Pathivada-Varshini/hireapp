package com.hireportal.demo.dto;

import com.hireportal.demo.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ApplicationDTO {

    private Long applicationId;

    private Date applicationDate;

    @NotNull(message = "Application status is required")
    private ApplicationStatus status;

    @NotNull(message = "Job ID is required")
    private Long jobId;

    @NotNull(message = "User ID is required")
    private Long userId;
}