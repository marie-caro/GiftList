package com.example.giftlist.repository;

import com.example.giftlist.dto.PersonResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.giftlist.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Page<Person> findByRelationship(String relationship, Pageable pageable);
}
