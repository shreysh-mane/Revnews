package com.revature.revnews.repository;

import com.revature.revnews.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {

    List<NewsArticle> findByCategoryId(Long categoryId);
}
