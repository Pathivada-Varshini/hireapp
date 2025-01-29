package com.hireportal.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CategoryDTO {

    private Long categoryId;

    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;

    private String categoryDescription;
}