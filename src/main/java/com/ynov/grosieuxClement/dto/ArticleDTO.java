package com.ynov.grosieuxClement.dto;

public class ArticleDTO {
    private String name;
    private Integer quantity;

    // Constructeur, getters et setters
    public ArticleDTO(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
