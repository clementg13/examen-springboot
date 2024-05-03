package com.ynov.grosieuxClement.services;

import com.ynov.grosieuxClement.models.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    List<Article> getAllArticles();

    Article addArticle(Article article);

    Optional<Article> getArticleById(Long id);

    Article updateArticle(Article article);

    Object deleteArticle(Long productId);

    Object addStock(Long productId, int quantity);

    Object exitStock(Long productId, int quantity);
}
