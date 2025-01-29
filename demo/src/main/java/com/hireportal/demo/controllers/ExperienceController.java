package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.ExperienceDTO;
import com.hireportal.demo.models.Experience;
import com.hireportal.demo.services.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    @Autowired
    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<ExperienceDTO> createExperience(@Valid @RequestBody ExperienceDTO experienceDTO) {
        Experience createdExperience = experienceService.createExperience(convertToEntity(experienceDTO));
        return ResponseEntity.ok(convertToDTO(createdExperience));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<List<ExperienceDTO>> getAllExperienceDetails() {
        List<Experience> experiences = experienceService.getAllExperienceDetails();
        List<ExperienceDTO> experienceDTOs = experiences.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(experienceDTOs);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Void> deleteExperienceById(@PathVariable("id") Long experienceId) {
        experienceService.deleteExperienceById(experienceId);
        return ResponseEntity.noContent().build();
    }

    private ExperienceDTO convertToDTO(Experience experience) {
        ExperienceDTO dto = new ExperienceDTO();
        dto.setExperienceId(experience.getExperienceId());
        dto.setJobPosition(experience.getJobPosition());
        dto.setOfficeName(experience.getOfficeName());
        dto.setStartDate(experience.getStartDate());
        dto.setEndDate(experience.getEndDate());
        dto.setUserId(experience.getUser().getUserId());
        return dto;
    }

    private Experience convertToEntity(ExperienceDTO dto) {
        Experience experience = new Experience();
        experience.setExperienceId(dto.getExperienceId());
        experience.setJobPosition(dto.getJobPosition());
        experience.setOfficeName(dto.getOfficeName());
        experience.setStartDate(dto.getStartDate());
        experience.setEndDate(dto.getEndDate());
        return experience;
    }
}
