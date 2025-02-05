package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.LanguageDTO;
import com.hireportal.demo.models.Language;
import com.hireportal.demo.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    @PostMapping
    public ResponseEntity<Language> createLanguage(@Valid @RequestBody LanguageDTO languageDTO) {
        return ResponseEntity.ok(languageService.createLanguage(languageDTO));
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguageById(@PathVariable("id") Long languageId) {
        languageService.deleteLanguageById(languageId);
        return ResponseEntity.noContent().build();
    }
}
