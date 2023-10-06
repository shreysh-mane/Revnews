package com.revature.revnews.controller;

import com.revature.revnews.entity.Category;
import com.revature.revnews.exception.CategoryNotFoundException;
import com.revature.revnews.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        try {
            logger.info("Attempting to create a new category: {}", category.getName());
            Category createdCategory = categoryService.createCategory(category);
            logger.info("Category created successfully: {}", createdCategory.getName());
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create category: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            logger.info("Fetching all categories");
            List<Category> categories = categoryService.getAllCategories();
            logger.info("Fetched {} categories", categories.size());
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to fetch categories: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            logger.info("Fetching category by ID: {}", id);
            Category category = categoryService.getCategoryById(id);
            if (category != null) {
                logger.info("Category found: {}", category.getName());
                return new ResponseEntity<>(category, HttpStatus.OK);
            } else {
                logger.error("Category not found for ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (CategoryNotFoundException e) {
            logger.error("Category not found for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Failed to fetch category by ID: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            logger.info("Attempting to update category with ID: {}", id);
            Category updatedCategory = categoryService.updateCategory(id, category);
            if (updatedCategory != null) {
                logger.info("Category updated successfully: {}", updatedCategory.getName());
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            } else {
                logger.error("Category not found for ID: {}", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (CategoryNotFoundException e) {
            logger.error("Category not found for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Failed to update category with ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        try {
            logger.info("Attempting to delete category with ID: {}", id);
            categoryService.deleteCategory(id);
            logger.info("Category deleted successfully with ID: {}", id);
            return new ResponseEntity<>("Category deleted successfully.", HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            logger.error("Category not found for ID: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Failed to delete category with ID: {}", id, e);
            return new ResponseEntity<>("Failed to delete category.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
