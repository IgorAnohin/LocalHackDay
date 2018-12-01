package ru.undeground.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.undeground.User;

public class RuntimeUserStorage implements UserStorage {

  private final int INITIAL_USER_STORAGE_SIZE = 100;

  private Map<String, User> users;

  public RuntimeUserStorage(Map<String, User> users) {
    this.users = users;
  }

  public RuntimeUserStorage() {
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
  public Optional<User> getUserById(String userId) {
    return Optional.ofNullable(users.get(userId));
  }
}
