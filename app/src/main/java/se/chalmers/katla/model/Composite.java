package se.chalmers.katla.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a composite in the model.
 */
public class Composite implements IComposite {

    private String baseText;
    private List<String> inputs;

    public Composite() {
        baseText = new String();
        inputs = new ArrayList<String>();
    }

    /**
     * Returns the baseText that contains both the predefined baseText and any user input if there is any.
     * @return the complete text
     */
    @Override
    public String getText() {
        return baseText;
    }

    /**
     * Returns the baseText for this composite without any of the users input.
     * @return the predefined text
     */
    @Override
    public String getBaseText() {
        return baseText;
    }

    /**
     * Sets the base baseText for this composite.
     * @param text the baseText to set

     */
    @Override
    public void setBaseText(String text) {
        this.baseText = text;
    }

    /**
     * Sets the list of input activities that should be activated when this composite is clicked.
     * @param inputs The list of the inputs to activate in order.
     */
    @Override
    public void setInputs(List<String> inputs) {
        for(String string : inputs) {
            inputs.add(string);
        }
    }

    /**
     * Returns the list of input activities that should be activated when this composite is clicked.
     * @return The list the inputs to activate in order.
     */
    @Override
    public List<String> getInputs() {
        return inputs;
    }
}
