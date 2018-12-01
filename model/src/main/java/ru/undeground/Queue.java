package ru.undeground;

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

  @Wither
  UUID eventId;
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

  }
}
