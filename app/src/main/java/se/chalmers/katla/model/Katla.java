package se.chalmers.katla.model;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.chalmers.katla.kaltaSmsManager.IKatlaSmsManager;
import se.chalmers.katla.kaltaSmsManager.KatlaSmsManagerFactory;

/**
 * Created by Anton on 2014-10-02.
 */
public class Katla implements IKatla {

    private final int MAX_SMS_LENGTH = 160;

    private static Katla ourInstance;
    private String message = "Alla vill till himlen men få vill ju dö hgfghf fhg hgf hgf hgf ghf hgf hgf hgf hgf hgf hgf hgf hgf hgf hgf hgf hgf ghf hgf hgf hgffghgf hg fhgf hg fhgf hg fhgf hfjhgf hgfkghfkf ufk h khg khg g hej alla fina getter!";
    private String phone = "0707833811";
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
        CompositesXmlParser parser = new CompositesXmlParser();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("res/raw/composites.xml");

        try {
            categories = parser.parse(in);
        } catch (IOException e) {
            //TODO proper exception handling
            System.out.println(e);
        } catch (XmlPullParserException e) {
            System.out.println(e);
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
    public boolean sendMessage() {
        IKatlaSmsManager manager = KatlaSmsManagerFactory.createIKatlaSmsManager();

        if(message.length() <= MAX_SMS_LENGTH) {
            try {
                if (manager != null) {
                    // Added so you don't have to call with null null paramters as intents, but you can.
                    manager.sendTextMessage(getPhone(), null, message);
                    return true;
                }

            } catch (Exception e) {
                System.out.println("Stack trace for failure" + e.getStackTrace());
                return false;
            }
        }else{
            ArrayList<String> messageList = divideMessage(message);

            try {
                if (manager != null && messageList!= null) {
                    // Added so you don't have to call with null null paramters as intents, but you can.
                    manager.sendMultipartTextMessage(getPhone(), null, messageList);
                    return true;
                }

            } catch (Exception e) {
                System.out.println("Stack trace for failure" + e.getStackTrace());
                return false;
            }
        }

        return true;
        //TODO pendng intents for delivery report m.m
    }

    /**
     * Divides a long message into 160 character length chunks.
     * @param message the long message to divide
     * @return an arrayList containing the parts of the long message.
     */
    private ArrayList<String> divideMessage(String message){
        ArrayList<String> list = new ArrayList<String>();
        int index = 0;
        while (index<message.length()){
            list.add(message.substring(index, Math.min(index + MAX_SMS_LENGTH, message.length())));
            index += MAX_SMS_LENGTH;
        }

        return list;
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
