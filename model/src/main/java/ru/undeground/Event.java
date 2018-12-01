package ru.undeground;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

@Data
@AllArgsConstructor
public class Event {

  @Wither
  private UUID eventId;
  @Wither
  private String eventName;
  @Wither
  private String eventDescriptions;

  @Wither
  private User eventAdmin;
  @Wither
  private List<User> eventUsers;

  @Wither
  private List<String> geoLocations;
}
