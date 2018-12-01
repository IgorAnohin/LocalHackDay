package ru.undeground.storage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ru.undeground.Event;

public interface EventStorage {
  void createEvent(Event event);

  Optional<Event> getEventById(UUID eventId);

  Optional<Event> getEventByName(String eventName);

  List<Event> getEventsByLocation(String location);

  List<Event> getManagedEvents(String adminId);

  List<Event> getUserEvents(String userId);

  List<Event> getAllEvents();
}
