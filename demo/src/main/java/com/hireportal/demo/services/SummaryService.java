package com.hireportal.demo.services;

import com.hireportal.demo.dto.SummaryDTO;
import com.hireportal.demo.models.Summary;
import com.hireportal.demo.models.User;
import com.hireportal.demo.repository.SummaryRepository;
import com.hireportal.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SummaryService {

    private final SummaryRepository summaryRepository;
    private final UserRepository userRepository;

    @Autowired
    public SummaryService(SummaryRepository summaryRepository, UserRepository userRepository) {
        this.summaryRepository = summaryRepository;
        this.userRepository = userRepository;
    }

    public SummaryDTO createSummary(SummaryDTO summaryDTO) {
        User user = userRepository.findById(summaryDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Summary summary = new Summary();
        summary.setSummaryText(summaryDTO.getSummaryText());
        summary.setUser(user);

        Summary savedSummary = summaryRepository.save(summary);
        return mapToDTO(savedSummary);
    }

    public List<SummaryDTO> getAllSummaryDetails() {
        return summaryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteSummaryById(Long summaryId) {
        if (!summaryRepository.existsById(summaryId)) {
            throw new RuntimeException("Summary not found");
        }
        summaryRepository.deleteById(summaryId);
    }

    private SummaryDTO mapToDTO(Summary summary) {
        SummaryDTO summaryDTO = new SummaryDTO();
        summaryDTO.setSummaryId(summary.getSummaryId());
        summaryDTO.setSummaryText(summary.getSummaryText());
        summaryDTO.setUserId(summary.getUser().getUserId());
        return summaryDTO;
    }
}