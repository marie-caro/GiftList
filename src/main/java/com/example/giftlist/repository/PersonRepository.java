package com.example.giftlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.giftlist.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
