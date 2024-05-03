package com.ynov.grosieuxClement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Le nom est obligatoire")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "La description est obligatoire")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Le prix est obligatoire")
    @Min(value = 1, message = "Le prix doit être un entier positif supérieur à 0")
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}