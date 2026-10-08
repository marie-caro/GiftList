package com.example.giftlist.service;

import com.example.giftlist.dto.GiftPatchRequest;
import com.example.giftlist.dto.GiftRequest;
import com.example.giftlist.dto.GiftResponse;
import com.example.giftlist.exception.GiftListNotFoundException;
import com.example.giftlist.exception.GiftNotFoundException;
import com.example.giftlist.model.Category;
import com.example.giftlist.model.Gift;
import com.example.giftlist.model.GiftList;
import com.example.giftlist.repository.GiftListRepository;
import com.example.giftlist.repository.GiftRepository;

import org.springframework.stereotype.Service;

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

    private GiftResponse toResponse(Gift gift) {
        GiftResponse response = new GiftResponse();

        response.setId(gift.getId());
        response.setTitle(gift.getTitle());
        response.setCategory(gift.getCategory());
        response.setLink(gift.getLink());
        response.setPrice(gift.getPrice());

        return response;
    }

    public GiftResponse createGift(Long id, GiftRequest gift) {
        GiftList giftList = giftListRepository
                .findById(id)
                .orElseThrow(() -> new GiftListNotFoundException(id));

        Gift result = new Gift();
        result.setTitle(gift.getTitle());
        result.setPrice(gift.getPrice());
        result.setLink(gift.getLink());
        result.setCategory(gift.getCategory());
        result.setList(giftList);
        repository.save(result);
        return toResponse(result);
    }

    public GiftResponse readGift(Long id) {
        Optional<GiftResponse> optional = repository.findById(id).map(this::toResponse);
        return optional.orElseThrow(() -> new GiftNotFoundException(id));
    }

    public Page<GiftResponse> readByCategory(Category category, Pageable pageable) {
        return repository.findByCategory(category, pageable).map(this::toResponse);
    }

    public Page<GiftResponse> readAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public GiftResponse updateGift(Long id, GiftRequest updatedGift) {
        Optional<Gift> optional = repository.findById(id);
        Gift currentGift = optional.orElseThrow(() -> new GiftNotFoundException(id));

        currentGift.setCategory(updatedGift.getCategory());
        currentGift.setLink(updatedGift.getLink());
        currentGift.setPrice(updatedGift.getPrice());
        currentGift.setTitle(updatedGift.getTitle());

        repository.save(currentGift);
        return toResponse(currentGift);
    }

    public GiftResponse patchGift(Long id, GiftPatchRequest updatedGift) {
        Gift gift = repository.findById(id)
                .orElseThrow(() -> new GiftNotFoundException(id));

        if (updatedGift.getTitle() != null)
            gift.setTitle(updatedGift.getTitle());
        if (updatedGift.getPrice() != null)
            gift.setPrice(updatedGift.getPrice());
        if (updatedGift.getLink() != null)
            gift.setLink(updatedGift.getLink());
        if (updatedGift.getCategory() != null)
            gift.setCategory(updatedGift.getCategory());

        return toResponse(repository.save(gift));
    }

    public void deleteGift(Long id) {
        repository.findById(id).orElseThrow(() -> new GiftNotFoundException(id));
        repository.deleteById(id);
    }
}