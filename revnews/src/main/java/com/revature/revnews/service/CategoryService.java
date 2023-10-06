package com.revature.revnews.service;

import com.revature.revnews.entity.Category;
import com.revature.revnews.exception.CategoryNotFoundException;
import com.revature.revnews.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        try {
            logger.info("Creating a new category: {}", category.getName());
            return categoryRepository.save(category);
        } catch (Exception e) {
            logger.error("Failed to create category: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Category> getAllCategories() {
        try {
            logger.info("Fetching all categories");
            return categoryRepository.findAll();
        } catch (Exception e) {
            logger.error("Failed to fetch categories: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Category getCategoryById(Long id) {
        try {
            logger.info("Fetching category by ID: {}", id);
            return categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        } catch (Exception e) {
            logger.error("Failed to fetch category by ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Category updateCategory(Long id, Category category) {
        try {
            logger.info("Updating category with ID: {}", id);
            Category existingCategory = categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

            existingCategory.setName(category.getName());

            return categoryRepository.save(existingCategory);
        } catch (Exception e) {
            logger.error("Failed to update category with ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void deleteCategory(Long id) {
        try {
            logger.info("Deleting category with ID: {}", id);
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

            categoryRepository.delete(category);
        } catch (Exception e) {
            logger.error("Failed to delete category with ID: {}", e.getMessage(), e);
            throw e;
        }
    }
}
