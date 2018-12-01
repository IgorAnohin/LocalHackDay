package ru.undeground.storage;

import lombok.AllArgsConstructor;
import lombok.experimental.Wither;
import ru.undeground.Queue;
import ru.undeground.User;

@AllArgsConstructor
@Wither
public class QueueAddResult {

  QueueAddStatus status;

  User user;
  Queue queue;
}
