package com.hireportal.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployerDetailsDTO {

    private Long id;

    @NotBlank(message = "Company name cannot be blank")
    private String companyName;

    @NotNull(message = "User ID is required")
    private Long userId;
}