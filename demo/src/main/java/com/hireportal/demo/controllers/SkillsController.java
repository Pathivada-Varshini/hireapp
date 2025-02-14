package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.SkillsDTO;
import com.hireportal.demo.services.SkillsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillsController {

    private final SkillsService skillsService;

    @Autowired
    public SkillsController(SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<SkillsDTO> createSkill(@Valid @RequestBody SkillsDTO skillsDTO) {
        SkillsDTO createdSkill = skillsService.createSkill(skillsDTO);
        return ResponseEntity.ok(createdSkill);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<List<SkillsDTO>> getAllSkills() {
        return ResponseEntity.ok(skillsService.getAllSkills());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Void> deleteSkillById(@PathVariable("id") Long skillId) {
        skillsService.deleteSkillsById(skillId);
        return ResponseEntity.noContent().build();
    }
}