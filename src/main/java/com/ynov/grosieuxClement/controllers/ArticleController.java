package com.ynov.grosieuxClement.controllers;

import com.ynov.grosieuxClement.dto.ArticleDTO;
import com.ynov.grosieuxClement.models.Article;
import com.ynov.grosieuxClement.models.User;
import com.ynov.grosieuxClement.services.ArticleService;
import com.ynov.grosieuxClement.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Operation(summary = "Récupère tous les produits", description = "Retourne une liste de tous les produits disponibles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des produits récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = Article.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("products")
    public ResponseEntity<?> getAllProducts(){
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @Operation(summary = "Ajoute un nouveau produit", description = "Crée un nouveau produit avec les détails fournis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Article article){
        return ResponseEntity.ok(articleService.addArticle(article));
    }

    @Operation(summary = "Modifie un produit existant", description = "Met à jour les détails d'un produit existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou produit non trouvé")
    })
    @PutMapping("products/{productId}")
    public ResponseEntity<?> editProduct(@PathVariable Long productId, @RequestBody Article article){

//        // Utiliser productId pour récupérer l'article
//            Optional<Article> articleOptional = articleService.getArticleById(productId);
//        if(articleOptional.isEmpty())
//            return ResponseEntity.badRequest().body("Article not found");
//
//        // Utiliser userId pour récupérer l'utilisateur
//        Optional<User> userOptional = userService.getUserById(userId);
//        if(userOptional.isEmpty())
//            return ResponseEntity.badRequest().body("User not found");
//
//        // Vérifier si l'utilisateur est le propriétaire de l'article
//        if(articleOptional.get().getUser().getId() != userOptional.get().getId())
//            return ResponseEntity.badRequest().body("User is not the owner of the article");

        return ResponseEntity.ok(articleService.updateArticle(article));
    }

    @Operation(summary = "Supprime un produit", description = "Supprime un produit existant par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produit supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    @DeleteMapping("products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        return ResponseEntity.ok(articleService.deleteArticle(productId));
    }

    @Operation(summary = "Ajoute du stock à un produit", description = "Augmente la quantité en stock d'un produit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("stock/entry")
    public ResponseEntity<?> addStock(@RequestBody Map<String, String> request){
        Long productId = Long.parseLong(request.get("productId"));
        int quantity = Integer.parseInt(request.get("quantity"));
        return ResponseEntity.ok(articleService.addStock(productId, quantity));
    }

    @Operation(summary = "Retire du stock d'un produit", description = "Diminue la quantité en stock d'un produit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("stock/exit")
    public ResponseEntity<?> exitStock(@RequestBody Map<String, String> request){
        Long productId = Long.parseLong(request.get("productId"));
        int quantity = Integer.parseInt(request.get("quantity"));
        return ResponseEntity.ok(articleService.exitStock(productId, quantity));
    }

    @Operation(summary = "Récupère le rapport d'inventaire", description = "Retourne un rapport d'inventaire avec le nom et la quantité de chaque produit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rapport d'inventaire récupéré avec succès",
                    content = @Content(schema = @Schema(implementation = ArticleDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping("reports/inventory")
    public ResponseEntity<?> getInventory(){
        List<Article> articles = articleService.getAllArticles();
        List<ArticleDTO> articleDTOs = articles.stream()
                .map(article -> new ArticleDTO(article.getName(), article.getQuantity()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOs);
    }
}
