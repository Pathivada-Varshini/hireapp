package com.hireportal.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationDTO {

    private Long educationId;

    @NotBlank(message = "Degree cannot be blank")
    private String degree;

    @NotBlank(message = "Institution cannot be blank")
    private String institution;

    @Positive(message = "Start year must be positive")
    private int startYear;

    @Positive(message = "End year must be positive")
    private int endYear;

    @NotNull(message = "User ID is required")
    private Long userId;
}