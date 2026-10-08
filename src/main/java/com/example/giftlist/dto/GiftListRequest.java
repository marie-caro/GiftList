package com.example.giftlist.dto;

import com.example.giftlist.model.Gift;
import com.example.giftlist.model.Occasion;
import com.example.giftlist.model.Person;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class GiftListRequest {
    @NotBlank
    private String title;
    private Occasion occasion;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Occasion getOccasion() { return occasion; }
    public void setOccasion(Occasion occasion) { this.occasion = occasion; }
}
