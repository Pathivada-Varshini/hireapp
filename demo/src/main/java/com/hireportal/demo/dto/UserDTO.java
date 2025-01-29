package com.hireportal.demo.dto;

import com.hireportal.demo.enums.Role;
import com.hireportal.demo.models.User;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {

    private Long userId;

    @NotBlank(message = "User name cannot be blank")
    private String userName;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @Pattern(regexp = "^\\d{10}$", message = "Contact number must be 10 digits")
    private String contactNumber;

    private String address;

    @NotNull(message = "Role is required")
    @Pattern(regexp = "JOB_SEEKER|JOB_PROVIDER", message = "Invalid role. Allowed values are JOB_SEEKER or JOB_PROVIDER")
    private String role;

    private String companyName;

    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setContactNumber(user.getContactNumber());
        userDTO.setAddress(user.getAddress());
        userDTO.setRole(user.getRole().toString());

        if (user.getRole() == Role.JOB_PROVIDER && user.getEmployerDetails() != null) {
            userDTO.setCompanyName(user.getEmployerDetails().getCompanyName());
        }

        return userDTO;
    }
}