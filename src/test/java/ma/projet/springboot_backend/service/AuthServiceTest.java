package ma.projet.springboot_backend.service;


import ma.projet.springboot_backend.entity.User;
import ma.projet.springboot_backend.repo.UserRepository;
import ma.projet.springboot_backend.request.LoginRequest;
import ma.projet.springboot_backend.request.RegisterRequest;
import ma.projet.springboot_backend.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthService authService;

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

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(jwtTokenUtil.generateToken(any())).thenReturn("testToken");

        // Act
        String token = authService.login(loginRequest);

        // Assert
        assertEquals("testToken", token);
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).matches("password", "encodedPassword");
        verify(jwtTokenUtil, times(1)).generateToken(any());
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("notfound@example.com");

        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginRequest));
        verify(userRepository, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    void testLogin_InvalidPassword() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongPassword");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        // Act & Assert
        assertThrows(BadCredentialsException.class, () -> authService.login(loginRequest));
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).matches("wrongPassword", "encodedPassword");
    }

    @Test
    void testRegister_Success() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("newuser@example.com");
        registerRequest.setUsername("newuser");
        registerRequest.setPassword("password");

        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        authService.register(registerRequest);

        // Assert
        verify(userRepository, times(1)).existsByEmail("newuser@example.com");
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("existinguser@example.com");

        when(userRepository.existsByEmail("existinguser@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.register(registerRequest));
        verify(userRepository, times(1)).existsByEmail("existinguser@example.com");
    }
}
