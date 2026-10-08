package com.example.giftlist.dto;

import java.time.LocalDate;

public class PersonPatchRequest {
    private String name;
    private LocalDate birthday;
    private String relationship;

    public PersonPatchRequest() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
}
