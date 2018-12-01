package ru.undeground;

import java.util.List;
import java.util.UUID;
import lombok.experimental.Wither;


@Wither
public class Admin extends User {

  List<UUID> eventsList;

  public Admin(String userId, String name) {
    super(userId, name);
  }
}
