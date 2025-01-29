package com.hireportal.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class ExperienceDTO {

    private Long experienceId;

    @NotBlank(message = "Job position cannot be blank")
    private String jobPosition;

    @NotBlank(message = "Office name cannot be blank")
    private String officeName;

    @Positive(message = "Start date must be a positive year")
    private int startDate;

    @Positive(message = "End date must be a positive year")
    private int endDate;

    @NotNull(message = "User ID is required")
    private Long userId;
}