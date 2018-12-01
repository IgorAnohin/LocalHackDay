package ru.undeground.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import ru.undeground.Admin;
import ru.undeground.User;

public class RuntimeUserStorage implements UserStorage {

  private final int INITIAL_ADMIN_STORAGE_SIZE = 10;
  private final int INITIAL_USER_STORAGE_SIZE = 100;

  private Set<String> admins;
  private Map<String, User> users;

  public RuntimeUserStorage(Set<String> admins,
      Map<String, User> users) {
    this.admins = admins;
    this.users = users;
  }

  public RuntimeUserStorage() {
    this.admins = new HashSet<>(INITIAL_ADMIN_STORAGE_SIZE);
    this.users = new HashMap<>(INITIAL_USER_STORAGE_SIZE);
  }

  @Override
  public boolean createUser(User user) {
    if (users.containsKey(user.getUserId())) {
      return false;
    }

    users.put(user.getUserId(), user);
    return true;
  }

  @Override
  public boolean createAdmin(Admin admin) {
    if (admins.contains(admin.getUserId())) {
      return false;
    }

    if (!users.containsKey(admin.getUserId())) {
      users.put(admin.getUserId(), admin);
    }

    admins.add(admin.getUserId());
    return true;
  }

  @Override
  public Optional<User> getUserById(String userId) {
    return Optional.ofNullable(users.get(userId));
  }

  @Override
  public Optional<Admin> getAdminById(String adminId) {
    if (!admins.contains(adminId)) {
      return Optional.empty();
    }

    return Optional.ofNullable((Admin) users.get(adminId));
  }
}
