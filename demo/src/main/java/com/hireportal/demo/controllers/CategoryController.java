package com.hireportal.demo.controllers;

import com.hireportal.demo.dto.CategoryDTO;
import com.hireportal.demo.models.Category;
import com.hireportal.demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import com.hireportal.demo.dto.CategoryDTO;
import com.hireportal.demo.models.Category;
import com.hireportal.demo.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Job Provider can view all categories
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    // Job Provider can view a category by ID
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Job Provider can create a new category
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    // Job Provider can update an existing category
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("id") Long categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDTO));
    }

    // Job Provider can partially update an existing category
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @PatchMapping("/{id}")
    public ResponseEntity<Category> partiallyUpdateCategory(
            @PathVariable("id") Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.partiallyUpdateCategory(categoryId, categoryDTO));
    }

    // Job Provider can delete a category
    @PreAuthorize("hasAuthority('JOB_PROVIDER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}

