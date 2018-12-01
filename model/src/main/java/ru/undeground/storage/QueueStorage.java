package ru.undeground.storage;

import java.util.List;
import java.util.UUID;
import ru.undeground.Queue;

public interface QueueStorage {

  void createQueue(Queue queue);

  void closeQueue(UUID queueId);

  List<Queue> getEventsQueue(UUID eventId);

  void addUserToQueue(UUID queueId, String userId);

  void approveUser(UUID queueId, String userId);
}
