package com.hireportal.demo.services;

import com.hireportal.demo.dto.LanguageDTO;
import com.hireportal.demo.exceptions.NotFoundException;
import com.hireportal.demo.models.Language;
import com.hireportal.demo.models.User;
import com.hireportal.demo.repository.LanguageRepository;
import com.hireportal.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;

    @Autowired
    public LanguageService(LanguageRepository languageRepository, UserRepository userRepository) {
        this.languageRepository = languageRepository;
        this.userRepository = userRepository;
    }

    // Create Language with validation
    @Transactional
    public Language createLanguage(LanguageDTO languageDTO) {
        // Validate user existence
        User user = userRepository.findById(languageDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Convert DTO to Entity
        Language language = new Language();
        language.setLanguageName(languageDTO.getLanguageName());
        language.setProficiency(languageDTO.getProficiency());
        language.setUser(user);

        // Save and return the entity
        return languageRepository.save(language);
    }

    // Get All Languages
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    // Delete Language by ID
    @Transactional
    public void deleteLanguageById(Long languageId) {
        if (!languageRepository.existsById(languageId)) {
            throw new NotFoundException("Language not found");
        }
        languageRepository.deleteById(languageId);
    }
}