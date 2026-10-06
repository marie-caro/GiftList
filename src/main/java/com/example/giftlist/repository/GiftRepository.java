package com.example.giftlist.repository;

import com.example.giftlist.model.Category;
import com.example.giftlist.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.giftlist.model.Gift;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GiftRepository extends JpaRepository<Gift, Long> {
    Page<Gift> findByCategory(Category category, Pageable pageable);
}
