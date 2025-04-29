package Pitoshnaya.Impact.service;

import Pitoshnaya.Impact.entity.User;
import Pitoshnaya.Impact.repository.UserRepository;
import Pitoshnaya.Impact.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthService(
        AuthenticationManager authenticationManager,
        JwtService jwtService,
        UserRepository userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public String login(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    username,
                    password
                )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByUsername(username).orElseThrow();
            user.setTokenVersion(user.getTokenVersion() + 1);
            userRepository.save(user);
            return jwtService.generateToken(user);
        } catch (Exception e) {
            return "Bad credentials";
        }
    }
}
