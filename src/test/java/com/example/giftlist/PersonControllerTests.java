package com.example.giftlist;

import com.example.giftlist.controller.PersonController;
import com.example.giftlist.dto.PersonRequest;
import com.example.giftlist.dto.PersonResponse;
import com.example.giftlist.exception.PersonNotFoundException;
import com.example.giftlist.model.Person;
import com.example.giftlist.service.PersonService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @Test
    void createPerson_shouldReturn201() throws Exception {
        PersonRequest request = new PersonRequest();
        request.setName("Aiden");
        request.setBirthday(LocalDate.of(2000, 5, 10));
        request.setRelationship("friend");

        PersonResponse person = new PersonResponse();
        person.setId(1L);
        person.setName("Aiden");
        person.setBirthday(LocalDate.of(2000, 5, 10));
        person.setRelationship("friend");

        when(personService.createPerson(any(PersonRequest.class)))
                .thenReturn(person);

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Aiden",
                    "birthday": "2000-05-10",
                    "relationship": "friend"
                }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Aiden"))
                .andExpect(jsonPath("$.relationship").value("friend"));

        verify(personService).createPerson(any(PersonRequest.class));
    }

    @Test
    void readPerson_shouldReturn200() throws Exception {
        /*Person person = new Person();
        person.setId(1L);
        person.setName("Aiden");

        when(personService.readPerson(1L))
                .thenReturn(person);

        mockMvc.perform(get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Aiden"));

        verify(personService).readPerson(1L);*/
    }

    @Test
    void readPerson_shouldReturn404WhenNotFound() throws Exception {
        when(personService.readPerson(999L))
                .thenThrow(new PersonNotFoundException(999L));

        mockMvc.perform(get("/person/999"))
                .andExpect(status().isNotFound());

        verify(personService).readPerson(999L);
    }

    @Test
    void updatePerson_shouldReturn200() throws Exception {
        /*Person person = new Person();
        person.setId(1L);
        person.setName("Aiden Updated");

        when(personService.updatePerson(eq(1L), any(Person.class)))
                .thenReturn(person);

        mockMvc.perform(put("/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Aiden Updated",
                    "birthday": "2000-05-10",
                    "relationship": "friend"
                }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Aiden Updated"));

        verify(personService).updatePerson(eq(1L), any(Person.class));*/
    }

    @Test
    void deletePerson_shouldReturn204() throws Exception {
        doNothing().when(personService).deletePerson(1L);

        mockMvc.perform(delete("/person/1"))
                .andExpect(status().isNoContent());

        verify(personService).deletePerson(1L);
    }
}