package com.revature.revnews.service;

import com.revature.revnews.entity.NewsArticle;
import com.revature.revnews.exception.NewsArticleNotFoundException;
import com.revature.revnews.repository.NewsArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsArticleService {

    private static final Logger logger = LoggerFactory.getLogger(NewsArticleService.class);

    @Autowired
    private NewsArticleRepository newsArticleRepository;

    public NewsArticle createNewsArticle(NewsArticle newsArticle) {
        logger.info("Creating news article");
        return newsArticleRepository.save(newsArticle);
    }

    public List<NewsArticle> getAllNewsArticles() {
        logger.info("Fetching all news articles");
        return newsArticleRepository.findAll();
    }

    public NewsArticle getNewsArticleById(Long id) throws NewsArticleNotFoundException {
        logger.info("Fetching news article by ID: {}", id);
        return newsArticleRepository.findById(id)
                .orElseThrow(() -> new NewsArticleNotFoundException("News article not found with ID: " + id));
    }

    public List<NewsArticle> getNewsArticlesByCategory(Long categoryId) throws NewsArticleNotFoundException {
        logger.info("Fetching news articles by category ID: {}", categoryId);
        return newsArticleRepository.findByCategoryId(categoryId);
    }

    public NewsArticle updateNewsArticle(Long id, NewsArticle updatedArticle) throws NewsArticleNotFoundException {
        logger.info("Updating news article with ID: {}", id);
        NewsArticle existingArticle = newsArticleRepository.findById(id)
                .orElseThrow(() -> new NewsArticleNotFoundException("News article not found with ID: " + id));

        // Update the fields as needed
        
        System.out.println(existingArticle.getTitle()+" "+ existingArticle.getCategoryId());
        existingArticle.setTitle(updatedArticle.getTitle());
        existingArticle.setContent(updatedArticle.getContent());
        existingArticle.setCategoryId(updatedArticle.getCategoryId());
        
        System.out.println(existingArticle.getTitle()+" "+ existingArticle.getCategoryId());
        return newsArticleRepository.save(existingArticle);
    }

    public void deleteNewsArticle(Long id) throws NewsArticleNotFoundException {
        logger.info("Deleting news article with ID: {}", id);
        NewsArticle existingArticle = newsArticleRepository.findById(id)
                .orElseThrow(() -> new NewsArticleNotFoundException("News article not found with ID: " + id));
        newsArticleRepository.delete(existingArticle);
    }
}
