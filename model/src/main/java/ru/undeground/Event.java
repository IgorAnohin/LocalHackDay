package ru.undeground;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Wither;
import ru.undeground.location.Location;

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


  private List<Location> geoLocation;

  public Event() {
    this.eventUsers = new ArrayList<>(INITIAL_USER_LIST_SIZE);
  }

}
