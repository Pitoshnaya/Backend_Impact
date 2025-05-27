package pitoshnaya.impact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pitoshnaya.impact.entity.User;
import pitoshnaya.impact.repository.UserRepository;

@SpringBootTest
public class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Test
  public void testSaveUser() {
    User user = new User();
    user.setUsername("testuser13");
    user.setPassword("password1234");
    userRepository.save(user);

    Optional<User> foundUser = userRepository.findByUsername("testuser13");
    assertNotNull(foundUser);
    assertEquals("testuser13", foundUser.get().getUsername());
  }
}
// удалить данные после run-теста
// in-memory
