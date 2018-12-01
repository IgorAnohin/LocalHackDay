package ru.undeground;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Wither;

@Data
public class Event {

  private final int INITIAL_USER_LIST_SIZE = 10;

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

  public Event(String chatId, String eventName, String eventDescriptions,
      String eventAdminId, List<String> geoLocations) {
    this.chatId = chatId;
    this.eventName = eventName;
    this.eventDescriptions = eventDescriptions;
    this.eventAdminId = eventAdminId;
    this.eventUsers = new ArrayList<>(INITIAL_USER_LIST_SIZE);
    this.geoLocations = geoLocations;
  }
}
