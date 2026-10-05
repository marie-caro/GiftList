package com.example.giftlist.service;

import com.example.giftlist.exception.GiftListNotFoundException;
import com.example.giftlist.exception.GiftNotFoundException;
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

        Gift input = new Gift();
        input.setTitle("Nintendo Switch");
        input.setPrice(new BigDecimal("299.99"));
        input.setLink("https://example.com");
        input.setList(list);

        when(giftListRepository.findById(1L))
                .thenReturn(Optional.of(list));

        Gift result = service.createGift(input);

        assertEquals("Nintendo Switch", result.getTitle());
        assertEquals(new BigDecimal("299.99"), result.getPrice());
        assertEquals("https://example.com", result.getLink());
        assertEquals(list, result.getList());

        verify(repository).save(any(Gift.class));
    }

    @Test
    void createGift_shouldThrowWhenGiftListDoesNotExist() {
        GiftList list = new GiftList();
        list.setId(999L);

        Gift input = new Gift();
        input.setTitle("Nintendo Switch");
        input.setList(list);

        when(giftListRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftListNotFoundException.class,
                () -> service.createGift(input)
        );

        verify(repository, never()).save(any(Gift.class));
    }

    @Test
    void readGift_shouldReturnGift() {
        Gift gift = new Gift();
        gift.setTitle("Nintendo Switch");

        when(repository.findById(1L))
                .thenReturn(Optional.of(gift));

        Gift result = service.readGift(1L);

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

        Gift updated = new Gift();
        updated.setTitle("Nintendo Switch OLED");
        updated.setPrice(new BigDecimal("349.99"));
        updated.setLink("https://new.example.com");
        updated.setList(list);

        when(giftListRepository.findById(1L))
                .thenReturn(Optional.of(list));

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(existing))
                .thenReturn(existing);

        Gift result = service.updateGift(1L, updated);

        assertEquals("Nintendo Switch OLED", result.getTitle());
        assertEquals(new BigDecimal("349.99"), result.getPrice());
        assertEquals("https://new.example.com", result.getLink());
        assertEquals(list, result.getList());

        verify(repository).save(existing);
    }

    @Test
    void updateGift_shouldThrowWhenGiftDoesNotExist() {
        GiftList list = new GiftList();
        list.setId(1L);

        Gift updated = new Gift();
        updated.setList(list);

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
    void updateGift_shouldThrowWhenGiftListDoesNotExist() {
        GiftList list = new GiftList();
        list.setId(999L);

        Gift updated = new Gift();
        updated.setList(list);

        when(giftListRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                GiftListNotFoundException.class,
                () -> service.updateGift(1L, updated)
        );

        verify(repository, never()).save(any(Gift.class));
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