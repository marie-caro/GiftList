package com.example.giftlist.repository;

import com.example.giftlist.model.Category;
import com.example.giftlist.model.Gift;
import com.example.giftlist.model.Occasion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.giftlist.model.GiftList;

public interface GiftListRepository extends JpaRepository<GiftList, Long> {
    Page<GiftList> findByOccasion(Occasion occasion, Pageable pageable);
}
