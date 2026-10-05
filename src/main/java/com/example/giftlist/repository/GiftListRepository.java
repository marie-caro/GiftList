package com.example.giftlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.giftlist.model.GiftList;

public interface GiftListRepository extends JpaRepository<GiftList, Long> {

}
