package ru.undeground.storage;

public enum QueueAddStatus {
  SUCCESS,
  NEEDS_APPROVAL,
  QUEUE_NOT_EXISTS,
  USER_NOT_EXISTS,
  USER_ALREADY_IN_QUEUE,
  QUEUE_IS_CLOSED,
  FAIL;
}
