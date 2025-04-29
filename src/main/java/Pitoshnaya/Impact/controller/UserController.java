package Pitoshnaya.Impact.controller;

import Pitoshnaya.Impact.dto.AuthRequest;
import Pitoshnaya.Impact.dto.AuthResponse;
import Pitoshnaya.Impact.dto.UserRequest;
import Pitoshnaya.Impact.service.AuthService;
import Pitoshnaya.Impact.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        try {
            registrationService.register(request.username(), request.password());
            return ResponseEntity.ok(Map.of("message", "Пользователь успешно зарегистрирован"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String jwt = authService.login(authRequest.username(), authRequest.password());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
