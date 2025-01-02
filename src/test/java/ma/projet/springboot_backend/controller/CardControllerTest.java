package ma.projet.springboot_backend.controller;



import ma.projet.springboot_backend.entity.Card;
import ma.projet.springboot_backend.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CardControllerTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCard() {
        // Arrange
        Card card1 = new Card();
        Card card2 = new Card();
        when(cardService.getAllCard()).thenReturn(Arrays.asList(card1, card2));

        // Act
        ResponseEntity<List<Card>> response = cardController.getAllCard();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(cardService, times(1)).getAllCard();
    }

    @Test
    void testGetCardById_Found() {
        // Arrange
        Card card = new Card();
        card.setId(1L);
        when(cardService.getCardById(1L)).thenReturn(Optional.of(card));

        // Act
        ResponseEntity<Card> response = cardController.getCardById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        verify(cardService, times(1)).getCardById(1L);
    }

    @Test
    void testGetCardById_NotFound() {
        // Arrange
        when(cardService.getCardById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Card> response = cardController.getCardById(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(cardService, times(1)).getCardById(1L);
    }

    @Test
    void testCreateCard() {
        // Arrange
        Card card = new Card();
        card.setId(1L);
        when(cardService.createCard(card)).thenReturn(card);

        // Act
        ResponseEntity<Card> response = cardController.createCard(card);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        verify(cardService, times(1)).createCard(card);
    }

    @Test
    void testUpdateCard_Success() {
        // Arrange
        Card updatedCard = new Card();
        updatedCard.setId(1L);

        when(cardService.updateCard(eq(1L), any(Card.class))).thenReturn(updatedCard);

        // Act
        ResponseEntity<Card> response = cardController.updateCard(1L, updatedCard);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        verify(cardService, times(1)).updateCard(eq(1L), any(Card.class));
    }

    @Test
    void testUpdateCard_NotFound() {
        // Arrange
        Card updatedCard = new Card();
        when(cardService.updateCard(eq(1L), any(Card.class))).thenThrow(new RuntimeException("Card not found"));

        // Act
        ResponseEntity<Card> response = cardController.updateCard(1L, updatedCard);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(cardService, times(1)).updateCard(eq(1L), any(Card.class));
    }

    @Test
    void testDeleteCard_Success() {
        // Arrange
        doNothing().when(cardService).deleteCard(1L);

        // Act
        ResponseEntity<Void> response = cardController.deleteCard(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(cardService, times(1)).deleteCard(1L);
    }

    @Test
    void testDeleteCard_NotFound() {
        // Arrange
        doThrow(new RuntimeException("Card not found")).when(cardService).deleteCard(1L);

        // Act
        ResponseEntity<Void> response = cardController.deleteCard(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(cardService, times(1)).deleteCard(1L);
    }
}
