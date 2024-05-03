package com.ynov.grosieuxClement.servicesImplem;

import com.ynov.grosieuxClement.models.Article;
import com.ynov.grosieuxClement.repositories.ArticleRepo;
import com.ynov.grosieuxClement.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleImplem implements ArticleService {
    @Autowired
    ArticleRepo articleRepo;

    @Override
    public List<Article> getAllArticles() {
        return articleRepo.findAll();
    }

    @Override
    public Article addArticle(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public Optional<Article> getArticleById(Long id) {
        return articleRepo.findById(id);
    }

    @Override
    public Article updateArticle(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public Object deleteArticle(Long productId) {
        articleRepo.deleteById(productId);
        return "Article deleted";
    }

    @Override
    public Object addStock(Long productId, int quantity) {
        Optional<Article> articleOptional = articleRepo.findById(productId);
        if(articleOptional.isEmpty())
            return "Article not found";

        Article article = articleOptional.get();
        article.setQuantity(article.getQuantity() + quantity);
        articleRepo.save(article);
        return "Entrée enregistrée";
    }

    @Override
    public Object exitStock(Long productId, int quantity) {
        Optional<Article> articleOptional = articleRepo.findById(productId);
        if(articleOptional.isEmpty())
            return "Article not found";

        Article article = articleOptional.get();
        if(article.getQuantity() < quantity)
            return "Stock insuffisant";

        article.setQuantity(article.getQuantity() - quantity);
        articleRepo.save(article);
        return "Sortie enregistrée";
    }
}
