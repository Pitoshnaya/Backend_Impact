package Pitoshnaya.Impact.controller;

import Pitoshnaya.Impact.dto.AuthRequest;
import Pitoshnaya.Impact.dto.AuthResponse;
import Pitoshnaya.Impact.dto.UserRequest;
import Pitoshnaya.Impact.service.AuthService;
import Pitoshnaya.Impact.service.RegistrationService;
import Pitoshnaya.Impact.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController                          /////вспомни для чего эта аннотация
@RequestMapping("/api")            ///// аналогично
public class UserController {
    private final AuthService authService;
    private final RegistrationService registrationService;

    public UserController(UserService userService, AuthService authService, RegistrationService registrationService) {
        this.authService = authService;
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request){

        String error=registrationService.register(request.getUsername(),request.getPassword());

        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }

        return ResponseEntity.status(201).body(Map.of("message", "Пользователь успешно зарегистрирован"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String jwt = authService.login(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
