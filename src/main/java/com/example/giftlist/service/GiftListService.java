package com.example.giftlist.service;

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

    public GiftList createList(GiftList giftList) {
        Person person = personRepository.findById(giftList.getPerson().getId())
                .orElseThrow(() -> new PersonNotFoundException(giftList.getPerson().getId()));

        GiftList list = new GiftList();
        list.setTitle(giftList.getTitle());
        list.setOccasion(giftList.getOccasion());
        list.setGifts(new ArrayList<>());
        list.setPerson(person);
        repository.save(list);
        return list;
    }

    public GiftList readList(Long id) {
        Optional<GiftList> optional = repository.findById(id);
        GiftList list = optional.orElseThrow(() -> new GiftListNotFoundException(id));
        return list;
    }

    public List<Gift> readGifts(Long id) {
        return this.readList(id).getGifts();
    }

    public Page<GiftList> readByOccasion(Occasion occasion, Pageable pageable) {
        return repository.findByOccasion(occasion, pageable);
    }

    public Page<GiftList> readAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public GiftList updateList(Long id, GiftList updatedList) {
        Person person = personRepository.findById(updatedList.getPerson().getId())
                .orElseThrow(() -> new PersonNotFoundException(updatedList.getPerson().getId()));

        Optional<GiftList> optional = repository.findById(id);
        GiftList list = optional.orElseThrow(() -> new GiftListNotFoundException(id));

        list.setGifts(updatedList.getGifts());
        list.setOccasion(updatedList.getOccasion());
        list.setPerson(person);
        list.setTitle(updatedList.getTitle());
        repository.save(list);
        return list;
    }

    public void deleteList(Long id) {
        repository.findById(id).orElseThrow(() -> new GiftListNotFoundException(id));
        repository.deleteById(id);
    }
}