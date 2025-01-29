package com.hireportal.demo.dto;

import lombok.Data;
import java.util.List;

@Data
public class ResumeDTO {
    private Long userId;
    private String summaryText;
    private List<EducationDTO> educationList;
    private List<ExperienceDTO> experienceList;
    private List<LanguageDTO> languageList;
    private List<SkillsDTO> skillsList;
}