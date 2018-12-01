package ru.undeground.storage;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import ru.undeground.Queue;
import ru.undeground.User;

@Data
@Wither
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QueueApproveResult {

  QueueApproveStatus status;
  User user;
  Queue queue;
}
