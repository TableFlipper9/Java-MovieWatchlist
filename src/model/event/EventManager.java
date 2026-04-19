package model.event;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private static EventManager instance;

    private final List<EventListener> listeners = new ArrayList<>();

    private EventManager() {}

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void subscribe(EventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unsubscribe(EventListener listener) {
        listeners.remove(listener);
    }

    public void notify(MovieEvent event) {
        for (EventListener listener : listeners) {
            listener.update(event);
        }
    }
}