package com.example.giftlist.service;

import com.example.giftlist.dto.GiftListPatchRequest;
import com.example.giftlist.dto.GiftListRequest;
import com.example.giftlist.dto.GiftListResponse;
import com.example.giftlist.exception.GiftListNotFoundException;
import com.example.giftlist.exception.PersonNotFoundException;
import com.example.giftlist.model.GiftList;
import com.example.giftlist.model.Occasion;
import com.example.giftlist.model.Person;
import com.example.giftlist.repository.GiftListRepository;
import com.example.giftlist.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GiftListServiceTest {

    private GiftListRepository repository;
    private PersonRepository personRepository;
    private GiftListService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(GiftListRepository.class);
        personRepository = Mockito.mock(PersonRepository.class);
        service = new GiftListService(repository, personRepository);
    }

    @Test
    void createList_shouldCreateGiftList() {
        Person person = new Person();
        person.setId(1L);
        person.setName("Aiden");

        GiftListRequest input = new GiftListRequest();
        input.setTitle("Aiden Birthday");
        input.setOccasion(Occasion.BIRTHDAY);

        when(personRepository.findById(1L))
                .thenReturn(Optional.of(person));

        GiftListResponse result = service.createList(1L, input);

        assertEquals("Aiden Birthday", result.getTitle());
        assertEquals(Occasion.BIRTHDAY, result.getOccasion());
        verify(repository).save(any(GiftList.class));
    }

    @Test
    void createList_shouldThrowWhenPersonDoesNotExist() {
        Person person = new Person();
        person.setId(999L);

        GiftListRequest input = new GiftListRequest();
        input.setTitle("Nobody's List");

        when(personRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> service.createList(eq(1L), input));

        verify(repository, never()).save(any(GiftList.class));
    }

    @Test
    void readList_shouldReturnGiftList() {
        GiftList list = new GiftList();
        list.setTitle("Aiden Birthday");

        when(repository.findById(1L))
                .thenReturn(Optional.of(list));

        GiftListResponse result = service.readList(1L);

        assertEquals("Aiden Birthday", result.getTitle());
        verify(repository).findById(1L);
    }

    @Test
    void readList_shouldThrowWhenGiftListDoesNotExist() {
        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftListNotFoundException.class,
                () -> service.readList(999L)
        );
    }

    @Test
    void updateList_shouldUpdateGiftList() {
        Person person = new Person();
        person.setId(1L);

        GiftList existing = new GiftList();
        existing.setTitle("Aiden Birthday");
        existing.setOccasion(Occasion.BIRTHDAY);
        existing.setPerson(person);

        GiftListRequest updated = new GiftListRequest();
        updated.setTitle("Aiden Christmas");
        updated.setOccasion(Occasion.CHRISTMAS);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(existing))
                .thenReturn(existing);

        GiftListResponse result = service.updateList(1L, updated);

        assertEquals("Aiden Christmas", result.getTitle());
        assertEquals(Occasion.CHRISTMAS, result.getOccasion());

        verify(repository).save(existing);
    }

    @Test
    void updateList_shouldThrowWhenGiftListDoesNotExist() {
        Person person = new Person();
        person.setId(1L);

        GiftListRequest updated = new GiftListRequest();

        when(personRepository.findById(1L))
                .thenReturn(Optional.of(person));

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftListNotFoundException.class,
                () -> service.updateList(eq(999L), updated)
        );

        verify(repository, never()).save(any(GiftList.class));
    }

    @Test
    void patchList_shouldUpdateOnlyProvidedFields() {
        GiftList existing = new GiftList();
        existing.setTitle("Aiden Birthday");
        existing.setOccasion(Occasion.BIRTHDAY);

        GiftListPatchRequest updated = new GiftListPatchRequest();
        updated.setOccasion(Occasion.CHRISTMAS);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(existing))
                .thenReturn(existing);

        GiftListResponse result = service.patchList(1L, updated);

        assertEquals("Aiden Birthday", result.getTitle());
        assertEquals(Occasion.CHRISTMAS, result.getOccasion());

        verify(repository).save(existing);
    }

    @Test
    void patchList_shouldThrowWhenGiftListDoesNotExist() {
        GiftListPatchRequest updated = new GiftListPatchRequest();
        updated.setOccasion(Occasion.CHRISTMAS);

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftListNotFoundException.class,
                () -> service.patchList(999L, updated)
        );

        verify(repository, never()).save(any(GiftList.class));
    }

    @Test
    void deleteList_shouldDeleteGiftList() {
        GiftList list = new GiftList();

        when(repository.findById(1L))
                .thenReturn(Optional.of(list));

        service.deleteList(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deleteList_shouldThrowWhenGiftListDoesNotExist() {
        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftListNotFoundException.class,
                () -> service.deleteList(999L)
        );

        verify(repository, never()).deleteById(999L);
    }
}