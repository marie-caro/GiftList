package com.example.giftlist;

import com.example.giftlist.dto.PersonRequest;
import com.example.giftlist.dto.PersonResponse;
import com.example.giftlist.exception.PersonNotFoundException;
import com.example.giftlist.model.Person;
import com.example.giftlist.repository.PersonRepository;
import com.example.giftlist.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonServiceTests {

    private PersonRepository repository;
    private PersonService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PersonRepository.class);
        service = new PersonService(repository);
    }

    @Test
    void createPerson_shouldCreatePerson() {
        PersonRequest request = new PersonRequest();
        request.setName("Aiden");
        request.setBirthday(LocalDate.of(2000, 5, 10));
        request.setRelationship("friend");

        PersonResponse result = service.createPerson(request);

        assertEquals("Aiden", result.getName());
        assertEquals(LocalDate.of(2000, 5, 10), result.getBirthday());
        assertEquals("friend", result.getRelationship());

        verify(repository).save(any(Person.class));
    }

    @Test
    void readPerson_shouldReturnPerson() {
        Person person = new Person();
        person.setName("Aiden");

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        PersonResponse result = service.readPerson(1L);

        assertEquals("Aiden", result.getName());
        verify(repository).findById(1L);
    }

    @Test
    void readPerson_shouldThrowWhenPersonDoesNotExist() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                PersonNotFoundException.class,
                () -> service.readPerson(999L)
        );
    }

    @Test
    void updatePerson_shouldUpdatePerson() {
        Person existing = new Person();
        existing.setName("Aiden");
        existing.setBirthday(LocalDate.of(2000, 5, 10));
        existing.setRelationship("friend");

        PersonRequest updated = new PersonRequest();
        updated.setName("Aiden Smith");
        updated.setBirthday(LocalDate.of(2000, 5, 11));
        updated.setRelationship("best friend");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        PersonResponse result = service.updatePerson(1L, updated);

        assertEquals("Aiden Smith", result.getName());
        assertEquals(LocalDate.of(2000, 5, 11), result.getBirthday());
        assertEquals("best friend", result.getRelationship());

        verify(repository).save(existing);
    }

    @Test
    void updatePerson_shouldThrowWhenPersonDoesNotExist() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        PersonRequest updated = new PersonRequest();
        updated.setName("Nobody");

        assertThrows(
                PersonNotFoundException.class,
                () -> service.updatePerson(999L, updated)
        );

        verify(repository, never()).save(any(Person.class));
    }

    @Test
    void deletePerson_shouldDeletePerson() {
        Person person = new Person();

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.deletePerson(1L);

        verify(repository).delete(person);
    }

    @Test
    void deletePerson_shouldThrowWhenPersonDoesNotExist() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                PersonNotFoundException.class,
                () -> service.deletePerson(999L)
        );

        verify(repository, never()).delete(any(Person.class));
    }
}