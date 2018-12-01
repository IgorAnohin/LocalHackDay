package ru.undeground.storage;

import java.util.List;
import java.util.UUID;
import ru.undeground.Queue;

public interface QueueStorage {

  boolean createQueue(Queue queue);

  boolean closeQueue(UUID queueId);

  List<Queue> getEventQueues(String eventName);

  QueueAddStatus addUserToQueue(UUID queueId, String userId);

  QueueApproveStatus approveUser(UUID queueId, String userId);
}
