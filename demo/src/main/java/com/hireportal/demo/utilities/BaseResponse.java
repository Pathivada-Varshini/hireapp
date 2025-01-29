package com.hireportal.demo.utilities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private String messages;  // To hold error or success message
    private T data;  // Data or payload
    private int status;  // HTTP Status code (e.g., 401 for Unauthorized)
}
