package com.example.giftlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.giftlist.model.Gift;

public interface GiftRepository extends JpaRepository<Gift, Long> {

}
