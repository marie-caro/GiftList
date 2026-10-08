package com.example.giftlist.dto;

import com.example.giftlist.model.GiftList;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public class PersonResponse {
    private String name;
    private Long id;
    private LocalDate birthday;
    private String relationship;

    public PersonResponse() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
}
