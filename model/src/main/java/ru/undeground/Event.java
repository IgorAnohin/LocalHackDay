package ru.undeground;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;

@Data
@Wither
@AllArgsConstructor
public class Event {

  private final int INITIAL_USER_LIST_SIZE = 10;

  private String chatId;

  private String eventName;
  private String eventDescriptions;

  private String eventAdminId;
  private List<String> eventUsers;


  private String geoLocation;

  public Event() {
    this.eventUsers = new ArrayList<>(INITIAL_USER_LIST_SIZE);
  }

  public Event(String chatId, String eventName, String eventDescriptions,
      String eventAdminId, String geoLocation) {
    this.chatId = chatId;
    this.eventName = eventName;
    this.eventDescriptions = eventDescriptions;
    this.eventAdminId = eventAdminId;
    this.eventUsers = new ArrayList<>(INITIAL_USER_LIST_SIZE);
    this.geoLocation = geoLocation;
  }
}
