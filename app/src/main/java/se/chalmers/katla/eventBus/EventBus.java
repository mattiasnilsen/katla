package se.chalmers.katla.eventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel on 29/09/2014.
 */
public class EventBus {

    private List<EventListener> eventListenerList;
    private static EventBus instance;

    private EventBus() {
        this.eventListenerList = new ArrayList<EventListener>();
    }

    public static EventBus getInstance() {
        if (instance == null){
            instance = new EventBus();
        }
        return instance;
    }

    public void registerListener(EventListener eventListener) {
        eventListenerList.add(eventListener);
    }

    public void removeListener(EventListener eventListener) {
        eventListenerList.remove(eventListener);
    }

    public void sendEvent(String s, Object o) {
        for (EventListener ev : eventListenerList) {
            ev.receiveEvent(s, o);
        }
    }

}
