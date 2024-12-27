package com.example.backspring.services;

import com.example.backspring.entities.CardData;
import com.example.backspring.repository.CardDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardDataService {

    private final CardDataRepository cardDataRepository;

    public CardDataService(CardDataRepository cardDataRepository) {
        this.cardDataRepository = cardDataRepository;
    }

    public List<CardData> getAllCardData() {
        return cardDataRepository.findAll();
    }

    public Optional<CardData> getCardDataById(Long id) {
        return cardDataRepository.findById(id);
    }

    public CardData createCardData(CardData cardData) {
        return cardDataRepository.save(cardData);
    }

    public CardData updateCardData(Long id, CardData cardData) {
        return cardDataRepository.findById(id)
                .map(existingCardData -> {
                    existingCardData.setCardType(cardData.getCardType());
                    existingCardData.setStudentName(cardData.getStudentName());
                    existingCardData.setSchoolYear(cardData.getSchoolYear());
                    existingCardData.setStudentLevel(cardData.getStudentLevel());
                    existingCardData.setSchoolAddress(cardData.getSchoolAddress());
                    existingCardData.setUserId(cardData.getUserId());
                    return cardDataRepository.save(existingCardData);
                })
                .orElseThrow(() -> new RuntimeException("CardData not found with id " + id));
    }

    public void deleteCardData(Long id) {
        if (cardDataRepository.existsById(id)) {
            cardDataRepository.deleteById(id);
        } else {
            throw new RuntimeException("CardData not found with id " + id);
        }
    }
}
