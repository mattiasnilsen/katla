package se.chalmers.katla.model;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for the Composite object in the model.
 * Created by Mattias on 2014-10-03.
 */
public interface IComposite extends Serializable {

    /**
     * Returns the baseText that contains both the predefined baseText and any user input if there is any.
     * @return the complete text
     */
    public String getText();
    /**
     * Returns the baseText for this composite without any of the users input.
     * @return the predefined text
     */
    public String getBaseText();
    /**
     * Sets the base baseText for this composite.
     * @param text the baseText to set

     */
    public void setBaseText(String text);

    /**
     * Sets the list of input activities that should be activated when this composite is clicked.
     * @param inputs The list of the inputs to activate in order.
     */
    public void setInputs(List<String> inputs);
    /**
     * Returns the list of input activities that should be activated when this composite is clicked.
     * @return The list the inputs to activate in order.
     */
    public List<String> getInputs();

}
