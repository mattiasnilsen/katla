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

    /**
     * Gets the instance of the event bus.
     * @return the EventBus instance.
     */
    public static synchronized EventBus getInstance() {
        if (instance == null){
            instance = new EventBus();
        }
        return instance;
    }

    /**
     * Add a listener to the event bus.
     * @param eventListener the listener that wants to listen to the event bus.
     */
    public synchronized void registerListener(EventListener eventListener) {
        eventListenerList.add(eventListener);
    }

    /**
     * Remove a listener from the eventBus.
     * @param eventListener the listener that no longer wants to listen to the bus.
     */
    public synchronized void removeListener(EventListener eventListener) {
        eventListenerList.remove(eventListener);
    }

    /**
     * Use to send events to the listeners.
     * @param s the message of the event.
     * @param o the object with information relevant to the event.
     */
    public synchronized void sendEvent(String s, Object o) {
        for (EventListener ev : eventListenerList) {
            ev.receiveEvent(s, o);
        }
    }

}
