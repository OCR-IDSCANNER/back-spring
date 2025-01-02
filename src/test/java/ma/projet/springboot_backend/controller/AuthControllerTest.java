package ma.projet.springboot_backend.controller;



import ma.projet.springboot_backend.request.LoginRequest;
import ma.projet.springboot_backend.request.RegisterRequest;
import ma.projet.springboot_backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        String expectedToken = "mockJwtToken";
        when(authService.login(loginRequest)).thenReturn(expectedToken);

        // Act
        ResponseEntity<String> response = authController.login(loginRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedToken, response.getBody());
        verify(authService, times(1)).login(loginRequest);
    }

    @Test
    void testRegister_Success() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setUsername("testUser");
        registerRequest.setPassword("password");

        doNothing().when(authService).register(registerRequest);

        // Act
        ResponseEntity<?> response = authController.register(registerRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody());
        verify(authService, times(1)).register(registerRequest);
    }
}

