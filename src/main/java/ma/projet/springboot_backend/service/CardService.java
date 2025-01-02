package ma.projet.springboot_backend.service;

import ma.projet.springboot_backend.entity.Card;
import ma.projet.springboot_backend.repo.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getAllCard() {
        return cardRepository.findAll();
    }

    public Optional<Card> getCardById(Long id) {
        return cardRepository.findById(id);
    }

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Card updateCard(Long id, Card card) {
        return cardRepository.findById(id)
                .map(existingCard -> {
                    existingCard.setCardType(card.getCardType());
                    existingCard.setStudentName(card.getStudentName());
                    existingCard.setSchoolYear(card.getSchoolYear());
                    existingCard.setStudentLevel(card.getStudentLevel());
                    existingCard.setSchoolAddress(card.getSchoolAddress());
                    existingCard.setUser(card.getUser());
                    return cardRepository.save(existingCard);
                })
                .orElseThrow(() -> new RuntimeException("CardData not found with id " + id));
    }

    public void deleteCard(Long id) {
        if (cardRepository.existsById(id)) {
            cardRepository.deleteById(id);
        } else {
            throw new RuntimeException("CardData not found with id " + id);
        }
    }
}