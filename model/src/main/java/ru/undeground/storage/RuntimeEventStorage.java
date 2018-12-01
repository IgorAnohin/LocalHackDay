package ru.undeground.storage;

import ru.undeground.Event;

import java.util.*;
import java.util.stream.Collectors;

public class RuntimeEventStorage implements EventStorage {

    private final int INITIAL_STORAGE_SIZE = 100;

    private Map<String, Event> events;

    public RuntimeEventStorage() {
        this.events = new HashMap<>(INITIAL_STORAGE_SIZE);
    }

    public RuntimeEventStorage(Map<String, Event> events) {
        this.events = events;
    }

    @Override
    public void createEvent(Event event) {
        if (events.containsKey(event.getEventName())) {
            return;
        }

        events.put(event.getEventName(), event);
    }

    @Override
    public Optional<Event> getEventByName(String eventName) {
        return events.values()
                .stream()
                .filter(event -> event.getEventName().equals(eventName))
                .findFirst();
    }

    @Override
    public List<Event> getEventsByLocation(String location) {
        return events.values()
                .stream()
                .filter(event -> event.getGeoLocation().contains(location))
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
