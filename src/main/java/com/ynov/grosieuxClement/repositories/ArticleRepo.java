package com.ynov.grosieuxClement.repositories;

import com.ynov.grosieuxClement.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    Article save(Article article);
    List<Article> findAll();
    void deleteById(Long id);
    Optional<Article> findById(Long id);
}
