package ru.undeground.storage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RuntimeStorageManager {

  @Getter
  RuntimeQueueStorage queueStorage;

  @Getter
  RuntimeEventStorage eventStorage;

  @Getter
  RuntimeUserStorage userStorage;

  public RuntimeStorageManager() {
    eventStorage = new RuntimeEventStorage();
    userStorage = new RuntimeUserStorage();
    queueStorage = new RuntimeQueueStorage(userStorage);
  }
}
