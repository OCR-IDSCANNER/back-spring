package ma.projet.springboot_backend.service;

import ma.projet.springboot_backend.entity.User;
import ma.projet.springboot_backend.impl.UserDetailsImpl;
import ma.projet.springboot_backend.repo.UserRepository;
import ma.projet.springboot_backend.request.LoginRequest;
import ma.projet.springboot_backend.request.RegisterRequest;
import ma.projet.springboot_backend.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return jwtTokenUtil.generateToken(new UserDetailsImpl(user));
        }
        throw new BadCredentialsException("Invalid credentials");
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }
}