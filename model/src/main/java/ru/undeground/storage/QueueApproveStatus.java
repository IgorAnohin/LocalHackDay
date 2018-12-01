package ru.undeground.storage;

public enum QueueApproveStatus {
  QUEUE_IS_CLOSED,
  QUEUE_NOT_EXISTS,
  USER_NOT_EXISTS,
  USER_IS_NOT_IN_APPROVING,
  SUCCESS;

  public boolean isSuccess() {
    return this == SUCCESS;
  }
}
