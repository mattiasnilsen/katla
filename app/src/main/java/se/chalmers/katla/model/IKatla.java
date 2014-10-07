package se.chalmers.katla.model;


import java.util.Iterator;

/**
 * Created by Anton on 2014-10-03.
 */
public interface IKatla {

    /**
     *
     * @return an iterator over the different composite categories
     */
    public Iterator<ICategory> getCategories();

    /**
     * Adds a category to the list of composite categories
     * @param category the category to add
     */
    public void addCategory(ICategory category);

    /**
     * Removes a category from the list of composite categories
     * @param category the category to remove
     */
    public void removeCategory(ICategory category);

    /**
     * Moves a category to a specific index
     * @param category the category to move
     * @param index the index to move to
     */
    public void moveCategory(ICategory category, int index);
    /**
     * Adds a string of text to the message
     * @param string that you want to add to the end of the message
     */
    public void addStringToMessage(String string);

    /**
     * Gets the message
     * @return a string representation of the message
     */
    public String getMessage();

    /**
     * Sets the message
     * @param string to set the message to
     */
    public void setMessage(String string);

    /**
     * Gets the selected phone number
     * @return a string representation of the selected phone number
     */
    public String getPhone();

    /**
     * Save the state of the application model
     */
    public void saveData();

    /**
     * Send message to selected contact or phone number
     */
    public void sendMessage();

    /**
     * Calls the selected contact or phone number
     */
    public void phoneCall();

}
