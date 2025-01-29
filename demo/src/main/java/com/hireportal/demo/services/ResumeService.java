package com.hireportal.demo.services;

import com.hireportal.demo.dto.ResumeDTO;
import com.hireportal.demo.models.*;
import com.hireportal.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SummaryRepository summaryRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    // Create or update a resume
    public ResumeDTO createOrUpdateResume(ResumeDTO resumeDTO) {
        User user = userRepository.findById(resumeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Summary summary = summaryRepository.findByUser(user)
                .orElse(new Summary()); // If Summary doesn't exist, create a new one
        summary.setSummaryText(resumeDTO.getSummaryText());
        summary.setUser(user);
        summaryRepository.save(summary);

        return resumeDTO;
    }

    // View a resume
    public ResumeDTO viewResume(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Summary summary = summaryRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        ResumeDTO resumeDTO = new ResumeDTO();
        resumeDTO.setUserId(user.getUserId());
        resumeDTO.setSummaryText(summary.getSummaryText());
        return resumeDTO;
    }

    // Update specific fields of a resume
    public ResumeDTO updateResumePartial(Long userId, ResumeDTO updatedFields) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Summary summary = summaryRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        if (updatedFields.getSummaryText() != null) {
            summary.setSummaryText(updatedFields.getSummaryText());
        }

        summaryRepository.save(summary);

        ResumeDTO resumeDTO = new ResumeDTO();
        resumeDTO.setUserId(user.getUserId());
        resumeDTO.setSummaryText(summary.getSummaryText());
        return resumeDTO;
    }
}
