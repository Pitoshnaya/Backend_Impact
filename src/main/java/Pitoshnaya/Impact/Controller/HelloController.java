package Pitoshnaya.Impact.Controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @PermitAll
    @GetMapping("/")
    public String hello(){
        return "Hello Drezden!";
    }
}
