package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.CategoryDTO;
import com.hireportal.demo.exceptions.CategoryNotFoundException;
import com.hireportal.demo.models.Category;
import com.hireportal.demo.services.CategoryService;
import com.hireportal.demo.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long categoryId) {
        try {
            return categoryService.getCategoryById(categoryId)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = categoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("id") Long categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = categoryService.updateCategory(categoryId, categoryDTO);
            return ResponseEntity.ok(category);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Category> partiallyUpdateCategory(
            @PathVariable("id") Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        try {
            Category category = categoryService.partiallyUpdateCategory(categoryId, categoryDTO);
            return ResponseEntity.ok(category);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> deleteCategory(@PathVariable("id") Long categoryId) {
        BaseResponse<String> baseResponse = new BaseResponse<>();
        try {
            categoryService.deleteCategory(categoryId);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessages("Category deleted successfully.");
            return ResponseEntity.ok(baseResponse);
        } catch (CategoryNotFoundException e) {
            baseResponse.setStatus(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessages(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessages("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }
    }
}
