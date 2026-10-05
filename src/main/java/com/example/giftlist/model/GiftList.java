package com.example.giftlist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class GiftList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Occasion occasion;

    @JsonManagedReference
    @OneToMany(mappedBy = "list")
    private List<Gift> gifts;

    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Occasion getOccasion() { return occasion; }
    public void setOccasion(Occasion occasion) { this.occasion = occasion; }
    public List<Gift> getGifts() { return gifts; }
    public void setGifts(List<Gift> gifts) { this.gifts = gifts; }
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }
}
