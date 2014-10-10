package se.chalmers.katla.model;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anton on 2014-10-02.
 */
public class Katla implements IKatla {

    private static Katla ourInstance;
    private String message = "Alla vill till himlen men få vill ju dö";
    private String phone = "666 1337";
    private String contact = "Satan Elite";

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    private List<ICategory> categories;

    public static Katla getInstance() {
        if(ourInstance == null) {
            ourInstance = new Katla();
        }
        return ourInstance;
    }


    private Katla() {
        categories = new ArrayList<ICategory>();
        for(int i = 0; i<5; i++){ //Adding some default categories
          categories.add(new Category("Category " + i)); //TODO remove this when not needed
        }
    }

    @Override
    public Iterator<ICategory> getCategories() {
        return categories.listIterator();
    }

    @Override
    public void addCategory(ICategory category) {
        if(!categories.contains(category)) {
            categories.add(category);
        }
    }

    @Override
    public void setMessage(String string) {
        message = string;
    }

    @Override
    public void removeCategory(ICategory category) {
        categories.remove(category);
    }

    @Override
    public void moveCategory(ICategory category, int index) {
       categories.remove(category);
       categories.add(index, category);
    }

    @Override
    public void addStringToMessage(String string) {
        message = message + string;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void saveData() {

    }

    @Override
    public void sendMessage() {
       //sendMessageService
    }

    @Override
    public void phoneCall() {
        //Does it work to make an activity to use startActivity method??
        Activity activity = new Activity();
        Intent intent = new Intent(Intent.ACTION_CALL);
        // According to documentation ACTION_CALL can not call emergency numbers?
        intent.setData(Uri.parse("tel:" + phone));
        activity.startActivity(intent);
    }
}
