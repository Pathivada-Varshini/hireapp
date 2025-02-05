package com.hireportal.demo.services;

import com.hireportal.demo.dto.CategoryDTO;
import com.hireportal.demo.exceptions.CategoryNotFoundException;
import com.hireportal.demo.models.Category;
import com.hireportal.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (existingCategory.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        Category category = existingCategory.get();
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        return categoryRepository.save(category);
    }

    public Category partiallyUpdateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        if (categoryDTO.getCategoryName() != null) {
            existingCategory.setCategoryName(categoryDTO.getCategoryName());
        }
        if (categoryDTO.getCategoryDescription() != null) {
            existingCategory.setCategoryDescription(categoryDTO.getCategoryDescription());
        }

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found");
        }
        categoryRepository.deleteById(categoryId);
    }

}