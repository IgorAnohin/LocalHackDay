package ru.undeground.storage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import ru.undeground.Queue;
import ru.undeground.User;

@Data
@AllArgsConstructor
@Wither
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueueAddResult {

  QueueAddStatus status;

  User user;
  Queue queue;
}
