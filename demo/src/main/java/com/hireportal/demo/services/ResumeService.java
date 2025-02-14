package com.hireportal.demo.services;

import com.hireportal.demo.dto.ExperienceDTO;
import com.hireportal.demo.dto.ResumeDTO;
import com.hireportal.demo.models.*;
import com.hireportal.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.hireportal.demo.dto.EducationDTO;
import com.hireportal.demo.dto.ExperienceDTO;
import com.hireportal.demo.dto.SkillsDTO;
import com.hireportal.demo.dto.SummaryDTO;
import com.hireportal.demo.dto.LanguageDTO;
import com.hireportal.demo.dto.ResumeDTO;


@Service
public class ResumeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SummaryRepository summaryRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    public ResumeDTO createOrUpdateResume(ResumeDTO resumeDTO) {
        User user = userRepository.findById(resumeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Summary summary = summaryRepository.findByUser(user).orElse(new Summary());
        summary.setSummaryText(resumeDTO.getSummaryText());
        summary.setUser(user);
        summaryRepository.save(summary);

        if (resumeDTO.getExperienceList() != null) {
            resumeDTO.getExperienceList().forEach(experienceDTO -> {
                Experience experience = new Experience();
                experience.setOfficeName(experienceDTO.getOfficeName());
                experience.setJobPosition(experienceDTO.getJobPosition());
                experience.setStartDate(experienceDTO.getStartDate());
                experience.setEndDate(experienceDTO.getEndDate());
                experience.setUser(user);
                experienceRepository.save(experience);
            });
        }

        if (resumeDTO.getEducationList() != null) {
            resumeDTO.getEducationList().forEach(educationDTO -> {
                Education education = new Education();
                education.setDegree(educationDTO.getDegree());
                education.setInstitution(educationDTO.getInstitution());
                education.setStartYear(educationDTO.getStartYear());
                education.setEndYear(educationDTO.getEndYear());
                education.setUser(user);
                educationRepository.save(education);
            });
        }

        if (resumeDTO.getSkillsList() != null) {
            resumeDTO.getSkillsList().forEach(skillsDTO -> {
                Skills skills = new Skills();
                skills.setSkillName(skillsDTO.getSkillName());
                skills.setSkillDescription(skillsDTO.getSkillDescription());
                skills.setUser(user);
                skillsRepository.save(skills);
            });
        }

        if (resumeDTO.getLanguageList() != null) {
            resumeDTO.getLanguageList().forEach(languageDTO -> {
                Language language = new Language();
                language.setLanguageName(languageDTO.getLanguageName());
                language.setProficiency(languageDTO.getProficiency());
                language.setUser(user);
                languageRepository.save(language);
            });
        }

        return resumeDTO;
    }

    public ResumeDTO viewResume(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Summary summary = summaryRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        List<Experience> experiences = experienceRepository.findByUser(user);

        List<Education> educations = educationRepository.findByUser(user);


        List<Skills> skills = skillsRepository.findByUser(user);

        List<Language> languages = languageRepository.findByUser(user);

        ResumeDTO resumeDTO = new ResumeDTO();
        resumeDTO.setUserId(user.getUserId());
        resumeDTO.setSummaryText(summary.getSummaryText());

        resumeDTO.setExperienceList(experiences.stream()
                .map(exp -> new ExperienceDTO(
                        exp.getExperienceId(),
                        exp.getJobPosition(),
                        exp.getOfficeName(),
                        exp.getStartDate(),
                        exp.getEndDate(),
                        user.getUserId()))
                .collect(Collectors.toList()));


        resumeDTO.setEducationList(educations.stream()
                .map(edu -> new EducationDTO(
                        edu.getEducationId(),
                        edu.getDegree(),
                        edu.getInstitution(),
                        edu.getStartYear(),
                        edu.getEndYear(),
                        user.getUserId()))
                .collect(Collectors.toList()));


        resumeDTO.setSkillsList(skills.stream()
                .map(skill -> new SkillsDTO(
                        skill.getSkillId(),
                        skill.getSkillName(),
                        skill.getSkillDescription(),
                        user.getUserId()))
                .collect(Collectors.toList()));

        resumeDTO.setLanguageList(languages.stream()
                .map(lang -> new LanguageDTO(
                        lang.getLanguageId(),
                        lang.getLanguageName(),
                        lang.getProficiency(),
                        user.getUserId()))
                .collect(Collectors.toList()));

        return resumeDTO;
    }

    public ResumeDTO updateResumePartial(Long userId, ResumeDTO updatedFields) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Summary summary = summaryRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        if (updatedFields.getSummaryText() != null) {
            summary.setSummaryText(updatedFields.getSummaryText());
        }

        summaryRepository.save(summary);

        if (updatedFields.getExperienceList() != null) {
            updatedFields.getExperienceList().forEach(experienceDTO -> {
                Experience experience = new Experience();
                experience.setOfficeName(experienceDTO.getOfficeName());
                experience.setJobPosition(experienceDTO.getJobPosition());
                experience.setStartDate(experienceDTO.getStartDate());
                experience.setEndDate(experienceDTO.getEndDate());
                experience.setUser(user);
                experienceRepository.save(experience);
            });
        }

        if (updatedFields.getEducationList() != null) {
            updatedFields.getEducationList().forEach(educationDTO -> {
                Education education = new Education();
                education.setDegree(educationDTO.getDegree());
                education.setInstitution(educationDTO.getInstitution());
                education.setStartYear(educationDTO.getStartYear());
                education.setEndYear(educationDTO.getEndYear());
                education.setUser(user);
                educationRepository.save(education);
            });
        }

        if (updatedFields.getSkillsList() != null) {
            updatedFields.getSkillsList().forEach(skillsDTO -> {
                Skills skills = new Skills();
                skills.setSkillName(skillsDTO.getSkillName());
                skills.setSkillDescription(skillsDTO.getSkillDescription());
                skills.setUser(user);
                skillsRepository.save(skills);
            });
        }

        if (updatedFields.getLanguageList() != null) {
            updatedFields.getLanguageList().forEach(languageDTO -> {
                Language language = new Language();
                language.setLanguageName(languageDTO.getLanguageName());
                language.setProficiency(languageDTO.getProficiency());
                language.setUser(user);
                languageRepository.save(language);
            });
        }

        return updatedFields;
    }

    public void deleteResume(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        summaryRepository.deleteByUser(user);
        experienceRepository.deleteByUser(user);
        educationRepository.deleteByUser(user);
        skillsRepository.deleteByUser(user);
        languageRepository.deleteByUser(user);
    }
}