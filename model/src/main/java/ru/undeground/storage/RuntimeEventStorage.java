package ru.undeground.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.undeground.Event;
import ru.undeground.location.Location;

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
    public List<Event> getEventsByLocation(String locationLink) {
        return events.values()
                .stream()
                .filter(event -> {
                    for(Location loc : event.getGeoLocation()) {
                        if(loc.getViewLink().equals(locationLink)) {
                            return true;
                        }
                    }

                    return false;
                })
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
