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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GiftServiceTest {

    private GiftRepository repository;
    private GiftListRepository giftListRepository;
    private GiftService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(GiftRepository.class);
        giftListRepository = Mockito.mock(GiftListRepository.class);
        service = new GiftService(repository, giftListRepository);
    }

    @Test
    void createGift_shouldCreateGift() {
        GiftList list = new GiftList();
        list.setId(1L);

        GiftRequest input = new GiftRequest();
        input.setTitle("Nintendo Switch");
        input.setPrice(new BigDecimal("299.99"));
        input.setLink("https://example.com");

        when(giftListRepository.findById(1L))
                .thenReturn(Optional.of(list));

        GiftResponse result = service.createGift(1L, input);

        assertEquals("Nintendo Switch", result.getTitle());
        assertEquals(new BigDecimal("299.99"), result.getPrice());
        assertEquals("https://example.com", result.getLink());

        verify(repository).save(any(Gift.class));
    }

    @Test
    void createGift_shouldThrowWhenGiftListDoesNotExist() {
        GiftList list = new GiftList();
        list.setId(999L);

        GiftRequest input = new GiftRequest();
        input.setTitle("Nintendo Switch");

        when(giftListRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftListNotFoundException.class,
                () -> service.createGift(1L, input)
        );

        verify(repository, never()).save(any(Gift.class));
    }

    @Test
    void readGift_shouldReturnGift() {
        Gift gift = new Gift();
        gift.setTitle("Nintendo Switch");

        when(repository.findById(1L))
                .thenReturn(Optional.of(gift));

        GiftResponse result = service.readGift(1L);

        assertEquals("Nintendo Switch", result.getTitle());
        verify(repository).findById(1L);
    }

    @Test
    void readGift_shouldThrowWhenGiftDoesNotExist() {
        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftNotFoundException.class,
                () -> service.readGift(999L)
        );
    }

    @Test
    void updateGift_shouldUpdateGift() {
        GiftList list = new GiftList();
        list.setId(1L);

        Gift existing = new Gift();
        existing.setTitle("Nintendo Switch");
        existing.setPrice(new BigDecimal("299.99"));
        existing.setLink("https://old.example.com");
        existing.setList(list);

        GiftRequest updated = new GiftRequest();
        updated.setTitle("Nintendo Switch OLED");
        updated.setPrice(new BigDecimal("349.99"));
        updated.setLink("https://new.example.com");

        when(giftListRepository.findById(1L))
                .thenReturn(Optional.of(list));

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(existing))
                .thenReturn(existing);

        GiftResponse result = service.updateGift(1L, updated);

        assertEquals("Nintendo Switch OLED", result.getTitle());
        assertEquals(new BigDecimal("349.99"), result.getPrice());
        assertEquals("https://new.example.com", result.getLink());

        verify(repository).save(existing);
    }

    @Test
    void patchGift_shouldThrowWhenGiftDoesNotExist() {
        GiftPatchRequest updated = new GiftPatchRequest();
        updated.setPrice(new BigDecimal("349.99"));

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftNotFoundException.class,
                () -> service.patchGift(999L, updated)
        );

        verify(repository, never()).save(any(Gift.class));
    }

    @Test
    void updateGift_shouldThrowWhenGiftDoesNotExist() {
        GiftList list = new GiftList();
        list.setId(1L);

        GiftRequest updated = new GiftRequest();

        when(giftListRepository.findById(1L))
                .thenReturn(Optional.of(list));

        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftNotFoundException.class,
                () -> service.updateGift(999L, updated)
        );

        verify(repository, never()).save(any(Gift.class));
    }

    @Test
    void patchGift_shouldUpdateOnlyProvidedFields() {
        Gift existing = new Gift();
        existing.setTitle("Nintendo Switch");
        existing.setPrice(new BigDecimal("299.99"));
        existing.setLink("https://old.example.com");
        existing.setCategory(Category.ELECTRONICS);

        GiftPatchRequest updated = new GiftPatchRequest();
        updated.setPrice(new BigDecimal("349.99"));

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(existing))
                .thenReturn(existing);

        GiftResponse result = service.patchGift(1L, updated);

        assertEquals("Nintendo Switch", result.getTitle());
        assertEquals(new BigDecimal("349.99"), result.getPrice());
        assertEquals("https://old.example.com", result.getLink());
        assertEquals(Category.ELECTRONICS, result.getCategory());
        verify(repository).save(existing);
    }

    @Test
    void deleteGift_shouldDeleteGift() {
        Gift gift = new Gift();

        when(repository.findById(1L))
                .thenReturn(Optional.of(gift));

        service.deleteGift(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void deleteGift_shouldThrowWhenGiftDoesNotExist() {
        when(repository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftNotFoundException.class,
                () -> service.deleteGift(999L)
        );

        verify(repository, never()).deleteById(999L);
    }
}