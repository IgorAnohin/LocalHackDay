package ru.undeground.storage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RuntimeStorageManager {
  @Getter
  RuntimeQueueStorage queueStorage;

  @Getter
  RuntimeEventStorage eventStorage;

  @Getter
  RuntimeUserStorage userStorage;


}
