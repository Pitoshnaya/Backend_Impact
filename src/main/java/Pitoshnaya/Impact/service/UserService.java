package Pitoshnaya.Impact.service;

import Pitoshnaya.Impact.entity.User;
import Pitoshnaya.Impact.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service                            ////
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
    }

    public String register(String username, String password) {
        if (password.length() < 8) {
            return "Длина пароля должна быть не менее 8 символов";
        }
        if (repository.findByUsername(username).isPresent()) {
            return "Это имя уже занято";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        repository.save(user);

        return null;
    }
}
