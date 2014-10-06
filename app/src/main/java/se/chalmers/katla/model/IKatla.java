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
     * @param category the category you want to add
     */
    public void addCategory(ICategory category);

    /**
     *
     * @param string that you want to add to the end of the message
     */
    public void addStringToMessage(String string);

    /**
     *
     * @return a string representation of the message
     */
    public String getMessage();

    /**
     *
     * @return a string representation of the selected phone number
     */
    public String getPhone();

    /**
     * Save the state of the application model
     */
    public void saveData();

    /**
     * Send message to selected contact or phone number.
     */
    public void sendMessage();

}
