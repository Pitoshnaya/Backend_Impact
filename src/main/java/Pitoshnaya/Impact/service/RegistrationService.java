package Pitoshnaya.Impact.service;

import Pitoshnaya.Impact.entity.User;
import Pitoshnaya.Impact.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(String username, String password) {
        if (password.length() < 8) {
            return "Длина пароля должна быть не менее 8 символов";
        }
        if (userRepository.findByUsername(username).isPresent()) {
            return "Это имя уже занято";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return null;
    }
}
