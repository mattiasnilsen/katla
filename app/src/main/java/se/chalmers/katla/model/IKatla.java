package se.chalmers.katla.model;

import android.provider.ContactsContract;

/**
 * Created by Anton on 2014-10-03.
 */
public interface IKatla {

    /**
     *
     * @return
     */
    //public Iterator<Categories> getCategories();

    /**
     *
     * @param string
     */
    public void addStringToMessage(String string);


    /**
     * Call to save state of the application model
     */
    public void saveData();

    /**
     * Send message to selected contact or phonenumber.
     */
    public void sendMessage();

}
