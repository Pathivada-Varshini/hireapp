package com.hireportal.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LanguageDTO {

    private Long languageId;

    @NotBlank(message = "Language name cannot be blank")
    private String languageName;

    @NotBlank(message = "Proficiency cannot be blank")
    private String proficiency;

    @NotNull(message = "User ID is required")
    private Long userId;
}