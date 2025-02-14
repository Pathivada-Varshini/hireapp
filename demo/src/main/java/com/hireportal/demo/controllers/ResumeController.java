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

    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    @PostMapping("/createOrUpdate")
    public ResponseEntity<BaseResponse<ResumeDTO>> createOrUpdateResume(@RequestBody ResumeDTO resumeDTO) {
        BaseResponse<ResumeDTO> baseResponse = new BaseResponse<>();
        ResumeDTO updatedResume = resumeService.createOrUpdateResume(resumeDTO);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessages("Resume created/updated successfully");
        baseResponse.setData(updatedResume);
        return ResponseEntity.ok(baseResponse);
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    @GetMapping("/view/{userId}")
    public ResponseEntity<BaseResponse<ResumeDTO>> viewResume(@PathVariable Long userId) {
        BaseResponse<ResumeDTO> baseResponse = new BaseResponse<>();
        ResumeDTO resumeDTO = resumeService.viewResume(userId);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessages("Resume fetched successfully");
        baseResponse.setData(resumeDTO);
        return ResponseEntity.ok(baseResponse);
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER')")
    @PatchMapping("/update/{userId}")
    public ResponseEntity<BaseResponse<ResumeDTO>> updateResumePartial(
            @PathVariable Long userId,
            @RequestBody ResumeDTO resumeDTO) {
        BaseResponse<ResumeDTO> baseResponse = new BaseResponse<>();
        ResumeDTO updatedResume = resumeService.updateResumePartial(userId, resumeDTO);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessages("Resume updated successfully");
        baseResponse.setData(updatedResume);
        return ResponseEntity.ok(baseResponse);
    }
}