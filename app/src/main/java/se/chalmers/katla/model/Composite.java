package se.chalmers.katla.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Composite class implements the IComposite interface and represents a composite object in the model.
 */
public class Composite implements IComposite {

    private String baseText;
    private List<String> inputs;

    public Composite() {
        baseText = new String();
        inputs = new ArrayList<String>();
    }

    @Override
    public String getText() {
        return baseText;
    }

    @Override
    public String getBaseText() {
        return baseText;
    }

    @Override
    public void setBaseText(String text) {
        this.baseText = text;
    }

    @Override
    public void setInputs(List<String> inputs) {
        for(String string : inputs) {
            this.inputs.add(string);
        }
    }

    @Override
    public List<String> getInputs() {
        return inputs;
    }

    @Override
    public String toString() {
        return getBaseText();
    }
}
