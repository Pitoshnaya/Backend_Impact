package pitoshnaya.impact.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pitoshnaya.impact.entity.User;
import pitoshnaya.impact.repository.UserRepository;

@Service
public class RegistrationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void register(String username, String password) {
    if (password.length() < 8) {
      throw new IllegalArgumentException("Длина пароля должна быть не менее 8 символов");
    }
    if (userRepository.findByUsername(username).isPresent()) {
      throw new IllegalArgumentException("Это имя уже занято");
    }
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
  }
}
