package com.example.giftlist.controller;

import com.example.giftlist.model.GiftList;
import com.example.giftlist.model.Person;
import com.example.giftlist.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
public class PersonController {
    private final PersonService personService;
    PersonController(PersonService personService) { this.personService =  personService; }

    @Operation(summary = "Create person", description = "Create new person")
    @ApiResponse(responseCode = "201", description = "person created")
    @ApiResponse(responseCode = "400", description = "Invalid person data")
    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person) {
        return new ResponseEntity<>(personService.createPerson(person), HttpStatus.CREATED);
    }


    @Operation(summary = "Retrieve all persons", description = "Retrieve all persons")
    @ApiResponse(responseCode = "200", description = "all persons retrieved")
    @GetMapping("/person")
    public ResponseEntity<Page<Person>> readAll(@RequestParam(required = false) String relationship, Pageable pageable) {
        if (relationship != null)
            return ResponseEntity.ok(personService.readByRelationship(relationship, pageable));
        return ResponseEntity.ok(personService.readAll(pageable));
    }

    @Operation(summary = "Retrieve person's gift lists", description = "Retrieve all gift lists belonging to a person")
    @ApiResponse(responseCode = "200", description = "Gift lists retrieved")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @GetMapping("/person/{id}/giftList")
    public ResponseEntity<List<GiftList>> readGiftLists(@PathVariable long id) {
        return ResponseEntity.ok(personService.readGiftList(id));
    }

    @Operation(summary = "Read person", description = "Read person")
    @ApiResponse(responseCode = "200", description = "person read")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @GetMapping("/person/{id}")
    public ResponseEntity<Person> readPerson(@PathVariable Long id) {
        return ResponseEntity.ok(personService.readPerson(id));
    }

    @Operation(summary = "Modify person", description = "Modify person")
    @ApiResponse(responseCode = "200", description = "person modified")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @ApiResponse(responseCode = "400", description = "Invalid person data")
    @PutMapping("/person/{id}")
    public ResponseEntity<Person> modifyPerson(@PathVariable Long id, @Valid @RequestBody Person person) {
        return ResponseEntity.ok(personService.updatePerson(id, person));
    }

    @Operation(summary = "Partially modify person", description = "Modify only the provided person fields")
    @ApiResponse(responseCode = "200", description = "Person modified")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @PatchMapping("/person/{id}")
    public ResponseEntity<Person> patchPerson(@PathVariable Long id, @RequestBody Person updatedPerson) {
        return ResponseEntity.ok(personService.patchPerson(id, updatedPerson));
    }

    @Operation(summary = "Delete person", description = "Delete  person")
    @ApiResponse(responseCode = "204", description = "person deleted")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @DeleteMapping("/person/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}