package com.hireportal.demo.services;

import com.hireportal.demo.enums.Role;
import com.hireportal.demo.models.EmployerDetails;
import com.hireportal.demo.models.User;
import com.hireportal.demo.dto.UserDTO;
import com.hireportal.demo.exceptions.NotFoundException;
import com.hireportal.demo.exceptions.InvalidCredentialsException;
import com.hireportal.demo.repository.EmployerDetailsRepository;
import com.hireportal.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployerDetailsRepository employerDetailsRepository;


    @Autowired
    public UserService(UserRepository userRepository, EmployerDetailsRepository employerDetailsRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
		 this.passwordEncoder = passwordEncoder;
        this.employerDetailsRepository = employerDetailsRepository;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setContactNumber(userDTO.getContactNumber());
        user.setAddress(userDTO.getAddress());
        user.setRole(Role.valueOf(userDTO.getRole()));

        if (userDTO.getRole().equals("JOB_PROVIDER") && userDTO.getCompanyName() != null) {
            EmployerDetails employerDetails = new EmployerDetails();
            employerDetails.setCompanyName(userDTO.getCompanyName());
            employerDetails.setUser(user);
            user.setEmployerDetails(employerDetails);
        }

        return userRepository.save(user);
    }



    public User loginUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }
        User user = userOptional.get();


	  if (!passwordEncoder.matches(password, user.getPassword())) {
		  throw new InvalidCredentialsException("Invalid email or password."); }


        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found."));
        user.setUserName(userDTO.getUserName());
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    public User partialUpdateUser(Long userId, Map<String, Object> updates) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    existingUser.setFirstName((String) value);
                    break;
                case "lastName":
                    existingUser.setLastName((String) value);
                    break;
                case "email":
                    existingUser.setEmail((String) value);
                    break;
                case "password":
                    existingUser.setPassword(passwordEncoder.encode((String) value));
                    break;
                case "contactNumber":
                    existingUser.setContactNumber((String) value);
                    break;
                case "address":
                    existingUser.setAddress((String) value);
                    break;
                case "role":
                    existingUser.setRole(Role.valueOf(((String) value).toUpperCase()));
                    break;
                case "companyName":
                    if (existingUser.getRole() != Role.JOB_PROVIDER) {
                        throw new IllegalArgumentException("Company name can only be updated for JOB_PROVIDER role");
                    }

                    EmployerDetails employerDetails = existingUser.getEmployerDetails();
                    if (employerDetails == null) {
                        employerDetails = new EmployerDetails();
                        employerDetails.setUser(existingUser);
                    }
                    employerDetails.setCompanyName((String) value);
                    employerDetailsRepository.save(employerDetails);
                    existingUser.setEmployerDetails(employerDetails);
                    break;
                default:
                    throw new IllegalArgumentException("Field " + key + " is not updatable");
            }
        });

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }
}