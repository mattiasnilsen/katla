package se.chalmers.katla.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import se.chalmers.katla.eventBus.EventListener;

/**
 * Created by Kalior on 28/09/2014.
 */
    // Does not need to implement LinearLayout, what this class extends depends on which type your
    // .xml would have had.
    // It is also possibly to not have any .xml file related to the view and create the view
    // completely in code.
    // Should also listen to the eventBus.
public class ExampleView extends LinearLayout implements EventListener {

    /**
     * Interface for sending events from the view
     */
    public static interface ViewListener {
        // Everything that has to be told to the controller should be added here.
    }

    private ViewListener viewListener; // Inner interface
    // All view references here, for example buttons, strings, different fields etc.

    /**
     * .xml layout constructor
     */
    public ExampleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Other constructor things that needs to be made.
    }

    /**
     * Used to find and set references to the objects in .xml file.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // Find all references to buttons etc. here
    }

    /**
     * Sets the viewListener on the view
     * @param viewListener the view listener that wants to listen to this view.
     */
    public void setViewListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    /**
     * Method for receiving events from the event bus
     * @param s the message corresponding to the event
     * @param o the object corresponding to the event
     */
    public void receiveEvent(String s, Object o) {
        // If statements on string etc do what you want.
    }

}
