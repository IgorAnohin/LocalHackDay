package ru.undeground;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Wither;

@Data
public class Event {
  @Wither
  private String chatId;
  @Wither
  private UUID eventId;

  @Wither
  private String eventName;
  @Wither
  private String eventDescriptions;

  @Wither
  private String eventAdminId;
  @Wither
  private List<String> eventUsers;


  @Wither
  private List<String> geoLocations;


}
