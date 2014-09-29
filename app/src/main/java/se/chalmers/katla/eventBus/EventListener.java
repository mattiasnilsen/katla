package se.chalmers.katla.eventBus;

/**
 * Created by Joel on 29/09/2014.
 */
public interface EventListener {
    public void receiveEvent(String s, Object o);
}
