package ru.undeground.storage;

import java.util.List;
import java.util.UUID;
import ru.undeground.Event;

public interface EventStorage {
  void createEvent(Event event);

  Event getEventById(UUID eventId);

  List<Event> getEventsByLocation(String location);

  List<Event> getManagedEvents(String adminId);

  List<Event> getUserEvents(String userId);

  List<Event> getAllEvents();
}
