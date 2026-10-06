package com.example.giftlist.service;

import com.example.giftlist.exception.GiftListNotFoundException;
import com.example.giftlist.exception.GiftNotFoundException;
import com.example.giftlist.exception.PersonNotFoundException;
import com.example.giftlist.model.Category;
import com.example.giftlist.model.Gift;
import com.example.giftlist.model.GiftList;
import com.example.giftlist.model.Person;
import com.example.giftlist.repository.GiftListRepository;
import com.example.giftlist.repository.GiftRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class GiftService {
    private final GiftRepository repository;
    private final GiftListRepository giftListRepository;

    public GiftService(GiftRepository repository, GiftListRepository giftListRepository) {
        this.repository = repository;
        this.giftListRepository = giftListRepository;
    }

    public Gift createGift(Gift gift) {
        GiftList giftList = giftListRepository.findById(gift.getList().getId())
                .orElseThrow(() -> new GiftListNotFoundException(gift.getList().getId()));

        Gift result = new Gift();
        result.setTitle(gift.getTitle());
        result.setPrice(gift.getPrice());
        result.setLink(gift.getLink());
        result.setCategory(gift.getCategory());
        result.setList(giftList);
        repository.save(result);
        return result;
    }

    public Gift readGift(Long id) {
        Optional<Gift> optional = repository.findById(id);
        return optional.orElseThrow(() -> new GiftNotFoundException(id));
    }

    public Page<Gift> readByCategory(Category category, Pageable pageable) {
        return repository.findByCategory(category, pageable);
    }

    public Page<Gift> readAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Gift updateGift(Long id, Gift updatedGift) {
        GiftList giftList = giftListRepository.findById(updatedGift.getList().getId())
                .orElseThrow(() -> new GiftListNotFoundException(updatedGift.getList().getId()));

        Optional<Gift> optional = repository.findById(id);
        Gift currentGift = optional.orElseThrow(() -> new GiftNotFoundException(id));

        currentGift.setCategory(updatedGift.getCategory());
        currentGift.setLink(updatedGift.getLink());
        currentGift.setList(giftList);
        currentGift.setPrice(updatedGift.getPrice());
        currentGift.setTitle(updatedGift.getTitle());

        repository.save(currentGift);
        return currentGift;
    }

    public void deleteGift(Long id) {
        repository.findById(id).orElseThrow(() -> new GiftNotFoundException(id));
        repository.deleteById(id);
    }
}