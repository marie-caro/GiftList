package com.example.giftlist;

import com.example.giftlist.controller.GiftController;
import com.example.giftlist.exception.GiftNotFoundException;
import com.example.giftlist.model.Gift;
import com.example.giftlist.service.GiftService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GiftController.class)
class GiftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GiftService giftService;

    @Test
    void createGift_shouldReturn201() throws Exception {
        Gift gift = new Gift();
        gift.setId(1L);
        gift.setTitle("Nintendo Switch");
        gift.setPrice(new BigDecimal("299.99"));

        when(giftService.createGift(any(Gift.class)))
                .thenReturn(gift);

        mockMvc.perform(post("/gift")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Nintendo Switch",
                                    "price": 299.99
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Nintendo Switch"))
                .andExpect(jsonPath("$.price").value(299.99));

        verify(giftService).createGift(any(Gift.class));
    }

    @Test
    void readGift_shouldReturn200() throws Exception {
        Gift gift = new Gift();
        gift.setId(1L);
        gift.setTitle("Nintendo Switch");

        when(giftService.readGift(1L))
                .thenReturn(gift);

        mockMvc.perform(get("/gift/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Nintendo Switch"));

        verify(giftService).readGift(1L);
    }

    @Test
    void readGift_shouldReturn404WhenNotFound() throws Exception {
        when(giftService.readGift(999L))
                .thenThrow(new GiftNotFoundException(999L));

        mockMvc.perform(get("/gift/999"))
                .andExpect(status().isNotFound());

        verify(giftService).readGift(999L);
    }

    @Test
    void updateGift_shouldReturn200() throws Exception {
        Gift gift = new Gift();
        gift.setId(1L);
        gift.setTitle("Nintendo Switch OLED");

        when(giftService.updateGift(eq(1L), any(Gift.class)))
                .thenReturn(gift);

        mockMvc.perform(put("/gift/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "title": "Nintendo Switch OLED",
                                    "price": 349.99
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Nintendo Switch OLED"));

        verify(giftService).updateGift(eq(1L), any(Gift.class));
    }

    @Test
    void deleteGift_shouldReturn204() throws Exception {
        doNothing().when(giftService).deleteGift(1L);

        mockMvc.perform(delete("/gift/1"))
                .andExpect(status().isNoContent());

        verify(giftService).deleteGift(1L);
    }
}