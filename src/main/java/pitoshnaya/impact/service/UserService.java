package pitoshnaya.impact.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pitoshnaya.impact.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return repository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + username));
  }
}
