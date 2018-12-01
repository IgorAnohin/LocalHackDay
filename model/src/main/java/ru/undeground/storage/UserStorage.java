package ru.undeground.storage;

import java.util.Optional;
import org.telegram.telegrambots.api.objects.User;
import ru.undeground.Admin;

public interface UserStorage {

  void createUser(User user);

  void createAdmin(Admin admin);

  Optional<User> getUserById(String userId);

  Optional<Admin> getAdminById(String adminId);
}
