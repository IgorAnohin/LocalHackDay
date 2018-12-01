package ru.undeground.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import ru.undeground.Queue;
import ru.undeground.User;

public class RuntimeQueueStorage implements QueueStorage {

  private final int INITIAL_STORAGE_SIZE = 100;

  private Map<UUID, Queue> queues;

  private UserStorage userStorage;

  public RuntimeQueueStorage(UserStorage userStorage) {
    this.queues = new HashMap<>(INITIAL_STORAGE_SIZE);
    this.userStorage = userStorage;
  }

  public RuntimeQueueStorage(Map<UUID, Queue> queues, UserStorage userStorage) {
    this.queues = queues;
    this.userStorage = userStorage;
  }

  @Override
  public boolean createQueue(Queue queue) {
    if (queues.containsKey(queue.getQueueId())) {
      return false;
    }

    queues.put(queue.getQueueId(), queue);
    return true;
  }

  @Override
  public boolean closeQueue(UUID queueId) {
    Queue queue = queues.get(queueId);
    if (queue == null) {
      return false;
    }

    if (queue.getClosed()) {
      return false;
    }

    queue.setClosed(true);
    return true;
  }

  @Override
  public List<Queue> getEventQueues(String eventName) {
    return queues.values()
        .stream()
        .filter(queue -> queue.getEventName().equals(eventName))
        .collect(Collectors.toList());
  }

  @Override
  public QueueAddStatus addUserToQueue(UUID queueId, String userId) {
    Queue queue = queues.get(queueId);
    if (queue == null) {
      return QueueAddStatus.QUEUE_NOT_EXISTS;
    }

    if (queue.getClosed()) {
      return QueueAddStatus.QUEUE_IS_CLOSED;
    }

    Optional<User> userOptional = userStorage.getUserById(userId);
    if (!userOptional.isPresent()) {
      return QueueAddStatus.USER_NOT_EXISTS;
    }

    User user = userOptional.get();
    if (queue.getApprovable()) {
      queue.getApprovingUsers().add(user.getUserId());
      return QueueAddStatus.NEEDS_APPROVAL;
    } else {
      if (queue.getParticipatingUsers().contains(user.getUserId())) {
        return QueueAddStatus.USER_ALREADY_IN_QUEUE;
      } else {
        queue.getParticipatingUsers().add(user.getUserId());
        return QueueAddStatus.SUCCESS;
      }
    }
  }

  @Override
  public QueueApproveStatus approveUser(UUID queueId, String userId) {
    Queue queue = queues.get(queueId);
    if (queue == null) {
      return QueueApproveStatus.QUEUE_NOT_EXISTS;
    }

    if (queue.getClosed()) {
      return QueueApproveStatus.QUEUE_IS_CLOSED;
    }

    Optional<User> userOptional = userStorage.getUserById(userId);
    if (!userOptional.isPresent()) {
      return QueueApproveStatus.USER_NOT_EXISTS;
    }

    User user = userOptional.get();

    List<String> approvingList = queue.getApprovingUsers();
    List<String> participatingList = queue.getParticipatingUsers();
    if (!approvingList.contains(user.getUserId())) {
      return QueueApproveStatus.USER_IS_NOT_IN_APPROVING;
    }

    approvingList.remove(user.getUserId());
    participatingList.add(user.getUserId());
    return QueueApproveStatus.SUCCESS;
  }
}
