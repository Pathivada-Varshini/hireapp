package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.ResumeDTO;
import com.hireportal.demo.services.ResumeService;
import com.hireportal.demo.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // Create or update a resume (only JOB_SEEKER can update their resume)
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    @PostMapping("/createOrUpdate")
    public ResponseEntity<BaseResponse<ResumeDTO>> createOrUpdateResume(@RequestBody ResumeDTO resumeDTO) {
        BaseResponse<ResumeDTO> baseResponse = new BaseResponse<>();
        try {
            ResumeDTO updatedResume = resumeService.createOrUpdateResume(resumeDTO);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessages("Resume created/updated successfully");
            baseResponse.setData(updatedResume);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            baseResponse.setMessages("Error creating/updating resume: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
    }

    // View a resume (both JOB_SEEKER and JOB_PROVIDER can view resumes)
    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    @GetMapping("/view/{userId}")
    public ResponseEntity<BaseResponse<ResumeDTO>> viewResume(@PathVariable Long userId) {
        BaseResponse<ResumeDTO> baseResponse = new BaseResponse<>();
        try {
            ResumeDTO resumeDTO = resumeService.viewResume(userId);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessages("Resume fetched successfully");
            baseResponse.setData(resumeDTO);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessages("Error fetching resume: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        }
    }

    // Update specific fields of a resume using PATCH (only JOB_SEEKER can update their own resume)
    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    @PatchMapping("/update/{userId}")
    public ResponseEntity<BaseResponse<ResumeDTO>> updateResumePartial(
            @PathVariable Long userId,
            @RequestBody ResumeDTO resumeDTO) {
        BaseResponse<ResumeDTO> baseResponse = new BaseResponse<>();
        try {
            ResumeDTO updatedResume = resumeService.updateResumePartial(userId, resumeDTO);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessages("Resume updated successfully");
            baseResponse.setData(updatedResume);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            baseResponse.setMessages("Error updating resume: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
    }
}
