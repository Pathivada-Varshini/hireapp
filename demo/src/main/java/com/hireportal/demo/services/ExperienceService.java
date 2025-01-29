package com.hireportal.demo.services;

import com.hireportal.demo.models.Experience;
import com.hireportal.demo.models.User;
import com.hireportal.demo.repository.ExperienceRepository;
import com.hireportal.demo.repository.UserRepository;
import com.hireportal.demo.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExperienceService(ExperienceRepository experienceRepository, UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }

    public Experience createExperience(Experience experience) {
        Long userId = experience.getUser().getUserId(); // Use getUserId()
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        experience.setUser(user);
        return experienceRepository.save(experience);
    }

    public List<Experience> getAllExperienceDetails() {
        return experienceRepository.findAll();
    }

    public void deleteExperienceById(Long experienceId) {
        experienceRepository.deleteById(experienceId);
    }
}