package com.example.giftlist.dto;

import com.example.giftlist.model.Category;

import java.math.BigDecimal;

public class GiftPatchRequest {
    private String title;
    private BigDecimal price;
    private String link;
    private Category category;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}