package com.example.giftlist.controller;

import com.example.giftlist.model.Gift;
import com.example.giftlist.model.GiftList;
import com.example.giftlist.service.GiftListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GiftListController {
    private final GiftListService giftListService;

    public GiftListController(GiftListService giftListService) {
        this.giftListService = giftListService;
    }

    @Operation(summary = "Create gift list", description = "Create a new gift list")
    @ApiResponse(responseCode = "201", description = "Gift list created")
    @ApiResponse(responseCode = "400", description = "Invalid gift list data")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @PostMapping("/giftList")
    public ResponseEntity<GiftList> createGiftList(@Valid @RequestBody GiftList giftList) {
        return new ResponseEntity<>(giftListService.createList(giftList), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all gift lists", description = "Retrieve all gift lists")
    @ApiResponse(responseCode = "200", description = "Gift lists retrieved")
    @GetMapping("/giftList")
    public ResponseEntity<List<GiftList>> readAll() {
        return ResponseEntity.ok(giftListService.readAll());
    }

    @Operation(summary = "Retrieve gifts", description = "Retrieve all gifts belonging to a gift list")
    @ApiResponse(responseCode = "200", description = "Gifts retrieved")
    @ApiResponse(responseCode = "404", description = "Gift list not found")
    @GetMapping("/giftList/{id}/gift")
    public ResponseEntity<List<Gift>> readGifts(@PathVariable Long id) {
        return ResponseEntity.ok(giftListService.readGifts(id));
    }

    @Operation(summary = "Retrieve gift list", description = "Retrieve a gift list by its ID")
    @ApiResponse(responseCode = "200", description = "Gift list retrieved")
    @ApiResponse(responseCode = "404", description = "Gift list not found")
    @GetMapping("/giftList/{id}")
    public ResponseEntity<GiftList> readGiftList(@PathVariable Long id) {
        return ResponseEntity.ok(giftListService.readList(id));
    }

    @Operation(summary = "Modify gift list", description = "Modify a gift list")
    @ApiResponse(responseCode = "200", description = "Gift list modified")
    @ApiResponse(responseCode = "400", description = "Invalid gift list data")
    @ApiResponse(responseCode = "404", description = "Gift list or Person not found")
    @PutMapping("/giftList/{id}")
    public ResponseEntity<GiftList> modifyGiftList(@PathVariable Long id, @Valid @RequestBody GiftList giftList) {
        return ResponseEntity.ok(giftListService.updateList(id, giftList));
    }

    @Operation(summary = "Delete gift list", description = "Delete a gift list")
    @ApiResponse(responseCode = "204", description = "Gift list deleted")
    @ApiResponse(responseCode = "404", description = "Gift list not found")
    @DeleteMapping("/giftList/{id}")
    public ResponseEntity<Void> deleteGiftList(@PathVariable Long id) {
        giftListService.deleteList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}