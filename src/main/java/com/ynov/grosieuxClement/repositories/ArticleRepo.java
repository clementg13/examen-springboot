package com.ynov.grosieuxClement.repositories;

import com.ynov.grosieuxClement.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    Article save(Article article);
}
