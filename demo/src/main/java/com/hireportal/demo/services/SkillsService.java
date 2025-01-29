package com.hireportal.demo.services;

import com.hireportal.demo.dto.SkillsDTO;
import com.hireportal.demo.models.Skills;
import com.hireportal.demo.models.User;
import com.hireportal.demo.repository.SkillsRepository;
import com.hireportal.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillsService {

    private final SkillsRepository skillsRepository;
    private final UserRepository userRepository;

    @Autowired
    public SkillsService(SkillsRepository skillsRepository, UserRepository userRepository) {
        this.skillsRepository = skillsRepository;
        this.userRepository = userRepository;
    }

    public SkillsDTO createSkill(SkillsDTO skillsDTO) {
        User user = userRepository.findById(skillsDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Skills skill = new Skills();
        skill.setSkillName(skillsDTO.getSkillName());
        skill.setSkillDescription(skillsDTO.getSkillDescription());
        skill.setUser(user);

        Skills savedSkill = skillsRepository.save(skill);

        return mapToDTO(savedSkill);
    }

    public List<SkillsDTO> getAllSkills() {
        return skillsRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteSkillsById(Long skillId) {
        if (!skillsRepository.existsById(skillId)) {
            throw new RuntimeException("Skill not found");
        }
        skillsRepository.deleteById(skillId);
    }

    private SkillsDTO mapToDTO(Skills skill) {
        SkillsDTO skillsDTO = new SkillsDTO();
        skillsDTO.setSkillId(skill.getSkillId());
        skillsDTO.setSkillName(skill.getSkillName());
        skillsDTO.setSkillDescription(skill.getSkillDescription());
        skillsDTO.setUserId(skill.getUser().getUserId());
        return skillsDTO;
    }
}