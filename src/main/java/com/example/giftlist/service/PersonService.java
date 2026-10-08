package com.example.giftlist.service;

import com.example.giftlist.dto.GiftListResponse;
import com.example.giftlist.dto.PersonPatchRequest;
import com.example.giftlist.dto.PersonRequest;
import com.example.giftlist.dto.PersonResponse;
import com.example.giftlist.exception.PersonNotFoundException;
import com.example.giftlist.model.GiftList;
import org.springframework.stereotype.Service;
import com.example.giftlist.repository.PersonRepository;
import com.example.giftlist.model.Person;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    private PersonResponse toResponse(Person person) {
        PersonResponse response = new PersonResponse();

        response.setId(person.getId());
        response.setName(person.getName());
        response.setBirthday(person.getBirthday());
        response.setRelationship(person.getRelationship());
        return response;
    }

    private GiftListResponse toGiftListResponse(GiftList giftList) {
        GiftListResponse response = new GiftListResponse();

        response.setId(giftList.getId());
        response.setTitle(giftList.getTitle());
        response.setOccasion(giftList.getOccasion());
        return response;
    }

    public PersonResponse createPerson(PersonRequest person) {
        Person result = new Person();

        result.setName(person.getName());
        result.setBirthday(person.getBirthday());
        result.setRelationship(person.getRelationship());
        repository.save(result);
        return toResponse(result);
    }

    public PersonResponse readPerson(Long id) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        return toResponse(person);
    }

    public Page<PersonResponse> readAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toResponse);
    }

    public List<GiftListResponse> readGiftList(Long id) {
        Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        return person.getLists()
                .stream()
                .map(this::toGiftListResponse)
                .toList();
    }

    public Page<PersonResponse> readByRelationship(String relationship, Pageable pageable) {
        return repository.findByRelationship(relationship, pageable).map(this::toResponse);
    }

    public PersonResponse updatePerson(Long id, PersonRequest updatedPerson) {
        Person person = this.repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        person.setBirthday(updatedPerson.getBirthday());
        person.setName(updatedPerson.getName());
        person.setRelationship(updatedPerson.getRelationship());

        return toResponse(repository.save(person));
    }

    public PersonResponse patchPerson(Long id, PersonPatchRequest updatedPerson) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        if (updatedPerson.getName() != null)
            person.setName(updatedPerson.getName());
        if (updatedPerson.getBirthday() != null)
            person.setBirthday(updatedPerson.getBirthday());
        if (updatedPerson.getRelationship() != null)
            person.setRelationship(updatedPerson.getRelationship());

        return toResponse(repository.save(person));
    }

    public void deletePerson(Long id) {
        Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        repository.delete(person);
    }
}