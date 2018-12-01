package ru.undeground.storage;

import java.util.Optional;
import ru.undeground.User;

public interface UserStorage {

  boolean createUser(User user);

//  boolean createAdmin(Admin admin);

  Optional<User> getUserById(String userId);

//  Optional<Admin> getAdminById(String adminId);
}
