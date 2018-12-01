package ru.undeground.storage;

import java.util.List;
import java.util.UUID;
import ru.undeground.Queue;
import ru.undeground.QueueAddStatus;
import ru.undeground.QueueApproveStatus;

public interface QueueStorage {

  boolean createQueue(Queue queue);

  boolean closeQueue(UUID queueId);

  List<Queue> getEventQueues(UUID eventId);

  QueueAddStatus addUserToQueue(UUID queueId, String userId);

  QueueApproveStatus approveUser(UUID queueId, String userId);
}
