package com.revature.revnews.controller;

import com.revature.revnews.entity.NewsArticle;
import com.revature.revnews.exception.NewsArticleNotFoundException;
import com.revature.revnews.service.NewsArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/news-articles")
public class NewsArticleController {

    private static final Logger logger = LoggerFactory.getLogger(NewsArticleController.class);

    @Autowired
    private NewsArticleService newsArticleService;

    @PostMapping("/create")
    public ResponseEntity<NewsArticle> createNewsArticle(@RequestBody NewsArticle newsArticle) {
        try {
            NewsArticle createdArticle = newsArticleService.createNewsArticle(newsArticle);
            logger.info("Created news article with ID: {}", createdArticle.getId());
            return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to create news article: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsArticle>> getAllNewsArticles() {
        try {
            List<NewsArticle> newsArticles = newsArticleService.getAllNewsArticles();
            logger.info("Fetched all news articles. Count: {}", newsArticles.size());
            return new ResponseEntity<>(newsArticles, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to fetch news articles: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsArticle> getNewsArticleById(@PathVariable Long id) {
        try {
            NewsArticle newsArticle = newsArticleService.getNewsArticleById(id);
            logger.info("Fetched news article by ID: {}", id);
            return new ResponseEntity<>(newsArticle, HttpStatus.OK);
        } catch (NewsArticleNotFoundException e) {
            logger.error("News article not found: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Failed to fetch news article by ID: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<NewsArticle>> getNewsArticlesByCategory(@PathVariable Long categoryId) {
        try {
            List<NewsArticle> newsArticles = newsArticleService.getNewsArticlesByCategory(categoryId);
            logger.info("Fetched news articles by category ID: {}", categoryId);
            return new ResponseEntity<>(newsArticles, HttpStatus.OK);
        } catch (NewsArticleNotFoundException e) {
            logger.error("News articles not found for category ID: {}", categoryId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Failed to fetch news articles by category ID: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsArticle> updateNewsArticle(@PathVariable Long id, @RequestBody NewsArticle updatedArticle) {
        try {
            NewsArticle newsArticle = newsArticleService.updateNewsArticle(id, updatedArticle);
            logger.info("Updated news article with ID: {}", id);
            return new ResponseEntity<>(newsArticle, HttpStatus.OK);
        } catch (NewsArticleNotFoundException e) {
            logger.error("News article not found: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Failed to update news article: {}", e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNewsArticle(@PathVariable Long id) {
        try {
            newsArticleService.deleteNewsArticle(id);
            logger.info("Deleted news article with ID: {}", id);
            return new ResponseEntity<>("News article deleted successfully.", HttpStatus.OK);
        } catch (NewsArticleNotFoundException e) {
            logger.error("News article not found: {}", e.getMessage());
            return new ResponseEntity<>("News article not found.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Failed to delete news article: {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to delete news article.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
