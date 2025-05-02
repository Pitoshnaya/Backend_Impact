package Pitoshnaya.Impact.controller;

import Pitoshnaya.Impact.dto.AuthRequest;
import Pitoshnaya.Impact.dto.AuthResponse;
import Pitoshnaya.Impact.dto.RegisterResponse;
import Pitoshnaya.Impact.dto.RegisterRequest;
import Pitoshnaya.Impact.service.AuthService;
import Pitoshnaya.Impact.service.RegistrationService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    private final AuthService authService;
    private final RegistrationService registrationService;

    public UserController(AuthService authService, RegistrationService registrationService) {
        this.authService = authService;
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            registrationService.register(request.username(), request.password());
            return ResponseEntity.ok(
                new RegisterResponse("Пользователь успешно зарегистрирован")
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new RegisterResponse(e.getMessage())
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            String jwt = authService.login(authRequest.username(), authRequest.password());
            return ResponseEntity.ok(
                new AuthResponse(jwt)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("message", e.getMessage())
            );
        }
    }
}
