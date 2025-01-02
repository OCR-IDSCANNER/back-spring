package ma.projet.springboot_backend.service;


import ma.projet.springboot_backend.entity.Card;
import ma.projet.springboot_backend.repo.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCard() {
        // Arrange
        Card card1 = new Card();
        Card card2 = new Card();
        when(cardRepository.findAll()).thenReturn(Arrays.asList(card1, card2));

        // Act
        List<Card> cards = cardService.getAllCard();

        // Assert
        assertEquals(2, cards.size());
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    void testGetCardById_Success() {
        // Arrange
        Card card = new Card();
        card.setId(1L);
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));

        // Act
        Optional<Card> result = cardService.getCardById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(cardRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCardById_NotFound() {
        // Arrange
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Card> result = cardService.getCardById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(cardRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCard() {
        // Arrange
        Card card = new Card();
        when(cardRepository.save(card)).thenReturn(card);

        // Act
        Card result = cardService.createCard(card);

        // Assert
        assertNotNull(result);
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void testUpdateCard_Success() {
        // Arrange
        Card existingCard = new Card();
        existingCard.setId(1L);

        Card updatedCard = new Card();
        updatedCard.setCardType("Updated Type");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(existingCard));
        when(cardRepository.save(existingCard)).thenReturn(existingCard);

        // Act
        Card result = cardService.updateCard(1L, updatedCard);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Type", existingCard.getCardType());
        verify(cardRepository, times(1)).findById(1L);
        verify(cardRepository, times(1)).save(existingCard);
    }

    @Test
    void testUpdateCard_NotFound() {
        // Arrange
        Card updatedCard = new Card();
        when(cardRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> cardService.updateCard(1L, updatedCard));
        assertEquals("CardData not found with id 1", exception.getMessage());
        verify(cardRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteCard_Success() {
        // Arrange
        when(cardRepository.existsById(1L)).thenReturn(true);

        // Act
        cardService.deleteCard(1L);

        // Assert
        verify(cardRepository, times(1)).existsById(1L);
        verify(cardRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCard_NotFound() {
        // Arrange
        when(cardRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> cardService.deleteCard(1L));
        assertEquals("CardData not found with id 1", exception.getMessage());
        verify(cardRepository, times(1)).existsById(1L);
        verify(cardRepository, never()).deleteById(anyLong());
    }
}
