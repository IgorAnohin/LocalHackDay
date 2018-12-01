package ru.undeground.storage;

import java.util.Optional;
import org.telegram.telegrambots.api.objects.User;
import ru.undeground.Admin;

public interface UserStorage {

  boolean createUser(User user);

  boolean createAdmin(Admin admin);

  Optional<User> getUserById(String userId);

  Optional<Admin> getAdminById(String adminId);
}
