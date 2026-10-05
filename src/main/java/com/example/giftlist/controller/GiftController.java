package com.example.giftlist.controller;

import com.example.giftlist.model.Gift;
import com.example.giftlist.service.GiftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GiftController {
    private final GiftService giftService;

    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @Operation(summary = "Create gift", description = "Create a new gift")
    @ApiResponse(responseCode = "201", description = "Gift created")
    @ApiResponse(responseCode = "400", description = "Invalid gift data")
    @PostMapping("/gift")
    public ResponseEntity<Gift> createGift(@Valid @RequestBody Gift gift) {
        return new ResponseEntity<>(giftService.createGift(gift), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all gifts", description = "Retrieve all gifts")
    @ApiResponse(responseCode = "200", description = "Gifts retrieved")
    @GetMapping("/gift")
    public ResponseEntity<List<Gift>> readAll() {
        return ResponseEntity.ok(giftService.readAll());
    }

    @Operation(summary = "Retrieve gift", description = "Retrieve a gift by its ID")
    @ApiResponse(responseCode = "200", description = "Gift retrieved")
    @ApiResponse(responseCode = "404", description = "Gift not found")
    @GetMapping("/gift/{id}")
    public ResponseEntity<Gift> readGift(@PathVariable Long id) {
        return ResponseEntity.ok(giftService.readGift(id));
    }

    @Operation(summary = "Modify gift", description = "Modify a gift")
    @ApiResponse(responseCode = "200", description = "Gift modified")
    @ApiResponse(responseCode = "400", description = "Invalid gift data")
    @ApiResponse(responseCode = "404", description = "Gift or GiftList not found")
    @PutMapping("/gift/{id}")
    public ResponseEntity<Gift> modifyGift(@PathVariable Long id, @Valid @RequestBody Gift updatedGift) {
        return ResponseEntity.ok(giftService.updateGift(id, updatedGift));
    }

    @Operation(summary = "Delete gift", description = "Delete a gift")
    @ApiResponse(responseCode = "204", description = "Gift deleted")
    @ApiResponse(responseCode = "404", description = "Gift not found")
    @DeleteMapping("/gift/{id}")
    public ResponseEntity<Void> deleteGift(@PathVariable Long id) {
        giftService.deleteGift(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}