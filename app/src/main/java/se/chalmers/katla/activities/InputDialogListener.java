package se.chalmers.katla.activities;

/**
 * A common interface for listening on input dialogs.
 * An activity that instantiates an input dialog must implement this interface.
 */
public interface InputDialogListener {
    /**
     * This method is called when the user has chosen what to input.
     * @param input A formatted string with the user's chosen input.
     */
    public void receiveInput(String input);
}
