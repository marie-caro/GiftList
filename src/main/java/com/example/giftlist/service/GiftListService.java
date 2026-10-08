package com.example.giftlist.service;

import com.example.giftlist.dto.GiftListPatchRequest;
import com.example.giftlist.dto.GiftListRequest;
import com.example.giftlist.dto.GiftListResponse;
import com.example.giftlist.dto.GiftResponse;
import com.example.giftlist.exception.GiftListNotFoundException;
import com.example.giftlist.exception.PersonNotFoundException;
import com.example.giftlist.model.Gift;
import com.example.giftlist.model.GiftList;
import com.example.giftlist.model.Occasion;
import com.example.giftlist.model.Person;
import com.example.giftlist.repository.GiftListRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import com.example.giftlist.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class GiftListService {
    private final GiftListRepository repository;
    private final PersonRepository personRepository;

    public GiftListService(GiftListRepository repository, PersonRepository personRepository) {
        this.repository = repository;
        this.personRepository = personRepository;
    }

    private GiftListResponse toResponse(GiftList giftList) {
        GiftListResponse response = new GiftListResponse();

        response.setId(giftList.getId());
        response.setTitle(giftList.getTitle());
        response.setOccasion(giftList.getOccasion());

        return response;
    }

    private GiftResponse toGiftResponse(Gift gift) {
        GiftResponse response = new GiftResponse();

        response.setId(gift.getId());
        response.setTitle(gift.getTitle());
        response.setCategory(gift.getCategory());
        response.setLink(gift.getLink());
        response.setPrice(gift.getPrice());
        return response;
    }

    public GiftListResponse createList(Long id, GiftListRequest giftList) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        GiftList list = new GiftList();
        list.setTitle(giftList.getTitle());
        list.setOccasion(giftList.getOccasion());
        list.setGifts(new ArrayList<>());
        list.setPerson(person);
        repository.save(list);
        return toResponse(list);
    }

    public GiftListResponse readList(Long id) {
        Optional<GiftList> optional = repository.findById(id);
        GiftList list = optional.orElseThrow(() -> new GiftListNotFoundException(id));
        return toResponse(list);
    }

    public List<GiftResponse> readGifts(Long id) {
        GiftList giftList = repository.findById(id).orElseThrow(() -> new GiftListNotFoundException(id));

        return giftList.getGifts()
                .stream()
                .map(this::toGiftResponse)
                .toList();
    }

    public Page<GiftListResponse> readByOccasion(Occasion occasion, Pageable pageable) {
        return repository.findByOccasion(occasion, pageable).map(this::toResponse);
    }

    public Page<GiftListResponse> readAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public GiftListResponse updateList(Long id, GiftListRequest updatedList) {
        Optional<GiftList> optional = repository.findById(id);
        GiftList list = optional.orElseThrow(() -> new GiftListNotFoundException(id));

        list.setOccasion(updatedList.getOccasion());
        list.setTitle(updatedList.getTitle());
        repository.save(list);
        return toResponse(list);
    }

    public GiftListResponse patchList(Long id, GiftListPatchRequest updatedList) {
        GiftList list = repository.findById(id)
                .orElseThrow(() -> new GiftListNotFoundException(id));

        if (updatedList.getTitle() != null)
            list.setTitle(updatedList.getTitle());
        if (updatedList.getOccasion() != null)
            list.setOccasion(updatedList.getOccasion());

        return toResponse(repository.save(list));
    }

    public void deleteList(Long id) {
        repository.findById(id).orElseThrow(() -> new GiftListNotFoundException(id));
        repository.deleteById(id);
    }
}