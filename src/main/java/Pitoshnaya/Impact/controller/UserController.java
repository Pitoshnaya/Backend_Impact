package Pitoshnaya.Impact.controller;
import Pitoshnaya.Impact.dto.UserRequest;
import Pitoshnaya.Impact.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController                          /////вспомни для чего эта аннотация
@RequestMapping("/api")            ///// аналогично
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request){

        String error=service.register(request.getUsername(),request.getPassword());

        if (error != null) {
            return ResponseEntity.badRequest().body(Map.of("error", error));
        }

        return ResponseEntity.status(201).body(Map.of("message", "Пользователь успешно зарегистрирован"));
    }

}
