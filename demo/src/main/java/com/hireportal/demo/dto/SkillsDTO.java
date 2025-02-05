package com.hireportal.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SkillsDTO {

    private Long skillId;

    @NotBlank(message = "Skill name cannot be blank")
    private String skillName;

    private String skillDescription;

    @NotNull(message = "User ID is required")
    private Long userId;
}