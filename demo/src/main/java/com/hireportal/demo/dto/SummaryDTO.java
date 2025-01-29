package com.hireportal.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SummaryDTO {

    private Long summaryId;

    @NotBlank(message = "Summary text cannot be blank")
    private String summaryText;

    @NotNull(message = "User ID is required")
    private Long userId;
}