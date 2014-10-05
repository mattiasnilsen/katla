package se.chalmers.katla.model;

/**
 * Class that represents a composite in the model.
 */
public class Composite implements IComposite {

    private String baseText;

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
}
