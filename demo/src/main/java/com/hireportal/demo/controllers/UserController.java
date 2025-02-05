package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.UserDTO;
import com.hireportal.demo.models.User;
import com.hireportal.demo.services.UserService;
import com.hireportal.demo.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserDTO>> registerUser(@RequestBody UserDTO userDTO) {
        BaseResponse<UserDTO> baseResponse = new BaseResponse<>();
        try {
            User createdUser = userService.createUser(userDTO);
            UserDTO createdUserDTO = UserDTO.fromEntity(createdUser);
            baseResponse.setStatus(HttpStatus.CREATED.value());
            baseResponse.setMessages("Registration successful.");
            baseResponse.setData(createdUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessages("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> login(@RequestParam String email, @RequestParam String password) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        try {
            User user = userService.loginUser(email, password);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessages("Authentication successful.");
            baseResponse.setData("Welcome " + user.getEmail());
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            baseResponse.setMessages("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
        }
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<UserDTO>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        BaseResponse<List<UserDTO>> baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessages("Users retrieved successfully.");
        baseResponse.setData(users.stream().map(UserDTO::fromEntity).toList());
        return ResponseEntity.ok(baseResponse);
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<User>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        BaseResponse<User> baseResponse = new BaseResponse<>();
        try {
            User updatedUser = userService.updateUser(id, userDTO);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessages("User updated successfully.");
            baseResponse.setData(updatedUser);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessages("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse<User>> partialUpdateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        BaseResponse<User> baseResponse = new BaseResponse<>();
        try {
            User updatedUser = userService.partialUpdateUser(id, updates);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessages("User partially updated successfully.");
            baseResponse.setData(updatedUser);
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessages("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }
    }

    @PreAuthorize("hasAuthority('JOB_SEEKER') or hasAuthority('JOB_PROVIDER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteUser(@PathVariable Long id) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        try {
            userService.deleteUser(id);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessages("User deleted successfully.");
            return ResponseEntity.ok(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessages("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }
    }
}
