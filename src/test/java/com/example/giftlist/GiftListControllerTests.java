package com.example.giftlist;

import com.example.giftlist.controller.GiftListController;
import com.example.giftlist.exception.GiftListNotFoundException;
import com.example.giftlist.model.GiftList;
import com.example.giftlist.service.GiftListService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GiftListController.class)
class GiftListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GiftListService giftListService;

    @Test
    void createGiftList_shouldReturn201() throws Exception {
        GiftList list = new GiftList();
        list.setId(1L);
        list.setTitle("Aiden Birthday");

        when(giftListService.createList(any(GiftList.class)))
                .thenReturn(list);

        mockMvc.perform(post("/giftList")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "Aiden Birthday",
                                "occasion": "BIRTHDAY",
                                "person": {
                                    "id": 1
                                }
                            }
                            """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Aiden Birthday"));

        verify(giftListService).createList(any(GiftList.class));
    }

    @Test
    void readGiftList_shouldReturn200() throws Exception {
        GiftList list = new GiftList();
        list.setId(1L);
        list.setTitle("Aiden Birthday");

        when(giftListService.readList(1L))
                .thenReturn(list);

        mockMvc.perform(get("/giftList/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Aiden Birthday"));

        verify(giftListService).readList(1L);
    }

    @Test
    void readGiftList_shouldReturn404WhenNotFound() throws Exception {
        when(giftListService.readList(999L))
                .thenThrow(new GiftListNotFoundException(999L));

        mockMvc.perform(get("/giftList/999"))
                .andExpect(status().isNotFound());

        verify(giftListService).readList(999L);
    }

    @Test
    void updateGiftList_shouldReturn200() throws Exception {
        GiftList list = new GiftList();
        list.setId(1L);
        list.setTitle("Aiden Christmas");

        when(giftListService.updateList(eq(1L), any(GiftList.class)))
                .thenReturn(list);

        mockMvc.perform(put("/giftList/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "title": "Aiden Christmas",
                                "occasion": "CHRISTMAS",
                                "person": {
                                    "id": 1
                                }
                            }
                        """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Aiden Christmas"));

        verify(giftListService).updateList(eq(1L), any(GiftList.class));
    }

    @Test
    void deleteGiftList_shouldReturn204() throws Exception {
        doNothing().when(giftListService).deleteList(1L);

        mockMvc.perform(delete("/giftList/1"))
                .andExpect(status().isNoContent());

        verify(giftListService).deleteList(1L);
    }
}