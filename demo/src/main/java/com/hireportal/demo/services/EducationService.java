package com.hireportal.demo.services;

import com.hireportal.demo.dto.EducationDTO;
import com.hireportal.demo.models.Education;
import com.hireportal.demo.repository.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    // Create Education from EducationDTO
    public Education createEducation(EducationDTO educationDTO) {
        Education education = new Education();
        education.setDegree(educationDTO.getDegree());
        education.setInstitution(educationDTO.getInstitution());
        education.setStartYear(educationDTO.getStartYear());
        education.setEndYear(educationDTO.getEndYear());
        return educationRepository.save(education);
    }

    // Get all Education details
    public List<Education> getAllEducationDetails() {
        return educationRepository.findAll();
    }

    // Delete Education by ID
    public void deleteEducationById(Long educationId) {
        educationRepository.deleteById(educationId);
    }

    // Convert entity to DTO if needed (for response purposes)
    private EducationDTO convertToDTO(Education education) {
        EducationDTO educationDTO = new EducationDTO();
        educationDTO.setEducationId(education.getEducationId());
        educationDTO.setDegree(education.getDegree());
        educationDTO.setInstitution(education.getInstitution());
        educationDTO.setStartYear(education.getStartYear());
        educationDTO.setEndYear(education.getEndYear());
        return educationDTO;
    }

    // Convert DTO to entity if needed (for other purposes)
    private Education convertToEntity(EducationDTO educationDTO) {
        Education education = new Education();
        education.setDegree(educationDTO.getDegree());
        education.setInstitution(educationDTO.getInstitution());
        education.setStartYear(educationDTO.getStartYear());
        education.setEndYear(educationDTO.getEndYear());
        return education;
    }
}