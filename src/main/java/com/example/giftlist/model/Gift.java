package com.example.giftlist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String title;
    private BigDecimal price;
    private String link;

    @Enumerated(EnumType.STRING)
    private Category category;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "gift_list_id")
    private GiftList list;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public GiftList getList() { return list; }
    public void setList(GiftList list) { this.list = list; }
}
