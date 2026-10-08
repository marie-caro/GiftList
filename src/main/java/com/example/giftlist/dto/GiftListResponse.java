package com.example.giftlist.dto;

import com.example.giftlist.model.Occasion;

public class GiftListResponse {
    private Long id;
    private String title;
    private Occasion occasion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Occasion getOccasion() { return occasion; }
    public void setOccasion(Occasion occasion) { this.occasion = occasion; }
}
