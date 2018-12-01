package ru.undeground;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Queue {
  private final int INITIAL_PARTICIPATING_USERS_STORAGE_SIZE = 10;
  private final int INITIAL_APPROVING_USERS_STORAGE_SIZE = 10;

  @Wither
  String eventName;
  @Wither
  UUID queueId;

  @Wither
  String queueName;
  @Wither
  String description;

  @Wither
  Boolean approvable;
  @Wither
  Boolean closed;

  @Wither
  List<String> participatingUsers;

  @Wither
  List<String> approvingUsers;

  @Wither
  String geoLocation;

  public Queue() {
    this.queueId = UUID.randomUUID();
    this.participatingUsers = new ArrayList<>(INITIAL_PARTICIPATING_USERS_STORAGE_SIZE);
    this.approvingUsers = new ArrayList<>(INITIAL_APPROVING_USERS_STORAGE_SIZE);
  }
}
