package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.SummaryDTO;
import com.hireportal.demo.services.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/summaries")
public class SummaryController {

    private final SummaryService summaryService;

    @Autowired
    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<SummaryDTO> createSummary(@Valid @RequestBody SummaryDTO summaryDTO) {
        SummaryDTO createdSummary = summaryService.createSummary(summaryDTO);
        return ResponseEntity.ok(createdSummary);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<List<SummaryDTO>> getAllSummaryDetails() {
        return ResponseEntity.ok(summaryService.getAllSummaryDetails());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    public ResponseEntity<Void> deleteSummaryById(@PathVariable("id") Long summaryId) {
        summaryService.deleteSummaryById(summaryId);
        return ResponseEntity.noContent().build();
    }
}
