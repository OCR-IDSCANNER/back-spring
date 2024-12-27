package com.example.backspring.controllers;

import com.example.backspring.entities.CardData;
import com.example.backspring.services.CardDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
public class CardDataController {

    private final CardDataService cardDataService;

    public CardDataController(CardDataService cardDataService) {
        this.cardDataService = cardDataService;
    }

    @GetMapping
    public ResponseEntity<List<CardData>> getAllCardData() {
        List<CardData> cardDataList = cardDataService.getAllCardData();
        return ResponseEntity.ok(cardDataList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardData> getCardDataById(@PathVariable Long id) {
        return cardDataService.getCardDataById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CardData> createCardData(@RequestBody CardData cardData) {
        CardData createdCardData = cardDataService.createCardData(cardData);
        return new ResponseEntity<>(createdCardData, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardData> updateCardData(@PathVariable Long id, @RequestBody CardData cardData) {
        try {
            CardData updatedCardData = cardDataService.updateCardData(id, cardData);
            return ResponseEntity.ok(updatedCardData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCardData(@PathVariable Long id) {
        try {
            cardDataService.deleteCardData(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
