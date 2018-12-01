package ru.undeground.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import ru.undeground.Event;

public class RuntimeEventStorage implements EventStorage {

  private final int INITIAL_STORAGE_SIZE = 100;

  private Map<UUID, Event> events;

  public RuntimeEventStorage() {
    this.events = new HashMap<>(INITIAL_STORAGE_SIZE);
  }

  public RuntimeEventStorage(Map<UUID, Event> events) {
    this.events = events;
  }

  @Override
  public void createEvent(Event event) {
    if (events.containsKey(event.getEventId())) {
      return;
    }

    if (events.values().stream()
        .anyMatch(storageEvent -> !storageEvent.getChatId().equals(event.getChatId()))) {
      events.put(event.getEventId(), event);
    }
  }

  @Override
  public Event getEventById(UUID eventId) {
    return events.get(eventId);
  }

  @Override
  public List<Event> getEventsByLocation(String location) {
    return events.values()
        .stream()
        .filter(event -> event.getGeoLocations().contains(location))
        .collect(Collectors.toList());
  }

  @Override
  public List<Event> getManagedEvents(String adminId) {
    return events.values()
        .stream()
        .filter(event -> event.getEventAdminId().equals(adminId))
        .collect(Collectors.toList());
  }

  @Override
  public List<Event> getUserEvents(String userId) {
    return events.values()
        .stream()
        .filter(events -> events.getEventUsers().contains(userId))
        .collect(Collectors.toList());
  }

  @Override
  public List<Event> getAllEvents() {
    return new ArrayList<>(events.values());
  }
}
