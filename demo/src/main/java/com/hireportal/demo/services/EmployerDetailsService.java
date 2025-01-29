package com.hireportal.demo.services;

import com.hireportal.demo.dto.EmployerDetailsDTO;
import com.hireportal.demo.models.EmployerDetails;
import com.hireportal.demo.models.User;
import com.hireportal.demo.repository.EmployerDetailsRepository;
import com.hireportal.demo.repository.UserRepository;
import com.hireportal.demo.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerDetailsService {

    private final EmployerDetailsRepository employerDetailsRepository;
    private final UserRepository userRepository;

    @Autowired
    public EmployerDetailsService(EmployerDetailsRepository employerDetailsRepository, UserRepository userRepository) {
        this.employerDetailsRepository = employerDetailsRepository;
        this.userRepository = userRepository;
    }

    public EmployerDetails getEmployerDetailsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        return employerDetailsRepository.findByUser(user)
                .orElseThrow(() -> new NotFoundException("Employer details not found for user ID: " + userId));
    }

    public EmployerDetails updateEmployerDetails(Long userId, EmployerDetailsDTO updatedDetailsDTO) {
        EmployerDetails existingDetails = getEmployerDetailsByUserId(userId);
        if (updatedDetailsDTO.getCompanyName() != null) {
            existingDetails.setCompanyName(updatedDetailsDTO.getCompanyName());
        }

        return employerDetailsRepository.save(existingDetails);
    }
}