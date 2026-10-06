package com.example.giftlist.service;

import com.example.giftlist.exception.PersonNotFoundException;
import com.example.giftlist.model.GiftList;
import org.springframework.stereotype.Service;
import com.example.giftlist.repository.PersonRepository;
import com.example.giftlist.model.Person;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person createPerson(Person person) {
        Person result = new Person();

        result.setName(person.getName());
        result.setBirthday(person.getBirthday());
        result.setRelationship(person.getRelationship());
        repository.save(result);
        return result;
    }

    public Person readPerson(Long id) {
        Optional<Person> optional = repository.findById(id);

        return optional.orElseThrow(() -> new PersonNotFoundException(id));
    }

    public Page<Person> readAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<GiftList> readGiftList(Long id) {
        Person person = this.readPerson(id);
        return person.getLists();
    }

    public Page<Person> readByRelationship(String relationship, Pageable pageable) {
        return repository.findByRelationship(relationship, pageable);
    }

    public Person updatePerson(Long id, Person updatedPerson) {
        Person person = this.repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));

        person.setBirthday(updatedPerson.getBirthday());
        person.setName(updatedPerson.getName());
        person.setRelationship(updatedPerson.getRelationship());

        return repository.save(person);
    }

    public Person patchPerson(Long id, Person updatedPerson) {
        Person person = repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        if (updatedPerson.getName() != null)
            person.setName(updatedPerson.getName());
        if (updatedPerson.getBirthday() != null)
            person.setBirthday(updatedPerson.getBirthday());
        if (updatedPerson.getRelationship() != null)
            person.setRelationship(updatedPerson.getRelationship());

        return repository.save(person);
    }

    public void deletePerson(Long id) {
        Person person = repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        repository.delete(person);
    }
}