package com.example.giftlist.controller;

import com.example.giftlist.dto.GiftListPatchRequest;
import com.example.giftlist.dto.GiftListRequest;
import com.example.giftlist.dto.GiftListResponse;
import com.example.giftlist.dto.GiftResponse;
import com.example.giftlist.model.Occasion;
import com.example.giftlist.service.GiftListService;
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
public class GiftListController {
    private final GiftListService giftListService;

    public GiftListController(GiftListService giftListService) {
        this.giftListService = giftListService;
    }

    @Operation(summary = "Create gift list", description = "Create a new gift list")
    @ApiResponse(responseCode = "201", description = "Gift list created")
    @ApiResponse(responseCode = "400", description = "Invalid gift list data")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @PostMapping("/person/{id}/giftList")
    public ResponseEntity<GiftListResponse> createGiftList(@PathVariable Long id, @Valid @RequestBody GiftListRequest giftList) {
        return new ResponseEntity<>(giftListService.createList(id, giftList), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all gift lists", description = "Retrieve all gift lists")
    @ApiResponse(responseCode = "200", description = "Gift lists retrieved")
    @GetMapping("/giftList")
    public ResponseEntity<Page<GiftListResponse>> readAll(@RequestParam(required = false) Occasion occasion, Pageable pageable) {
        if (occasion != null)
            return ResponseEntity.ok(giftListService.readByOccasion(occasion, pageable));
        return ResponseEntity.ok(giftListService.readAll(pageable));
    }

    @Operation(summary = "Retrieve gifts", description = "Retrieve all gifts belonging to a gift list")
    @ApiResponse(responseCode = "200", description = "Gifts retrieved")
    @ApiResponse(responseCode = "404", description = "Gift list not found")
    @GetMapping("/giftList/{id}/gift")
    public ResponseEntity<List<GiftResponse>> readGifts(@PathVariable Long id) {
        return ResponseEntity.ok(giftListService.readGifts(id));
    }

    @Operation(summary = "Retrieve gift list", description = "Retrieve a gift list by its ID")
    @ApiResponse(responseCode = "200", description = "Gift list retrieved")
    @ApiResponse(responseCode = "404", description = "Gift list not found")
    @GetMapping("/giftList/{id}")
    public ResponseEntity<GiftListResponse> readGiftList(@PathVariable Long id) {
        return ResponseEntity.ok(giftListService.readList(id));
    }

    @Operation(summary = "Modify gift list", description = "Modify a gift list")
    @ApiResponse(responseCode = "200", description = "Gift list modified")
    @ApiResponse(responseCode = "400", description = "Invalid gift list data")
    @ApiResponse(responseCode = "404", description = "Gift list or Person not found")
    @PutMapping("/giftList/{id}")
    public ResponseEntity<GiftListResponse> modifyGiftList(@PathVariable Long id, @Valid @RequestBody GiftListRequest giftList) {
        return ResponseEntity.ok(giftListService.updateList(id, giftList));
    }

    @Operation(summary = "Partially modify gift list", description = "Modify only the provided gift list fields")
    @ApiResponse(responseCode = "200", description = "Gift list modified")
    @ApiResponse(responseCode = "404", description = "Gift list not found")
    @PatchMapping("/giftList/{id}")
    public ResponseEntity<GiftListResponse> patchGiftList(@PathVariable Long id, @RequestBody GiftListPatchRequest updatedList) {
        return ResponseEntity.ok(giftListService.patchList(id, updatedList));
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