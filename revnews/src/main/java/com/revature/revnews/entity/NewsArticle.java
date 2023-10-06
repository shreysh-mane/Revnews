package com.revature.revnews.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "news_articles")
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    
}
