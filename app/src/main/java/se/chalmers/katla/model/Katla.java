package se.chalmers.katla.model;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anton on 2014-10-02.
 */
public class Katla implements IKatla {

    private static Katla ourInstance;
    private String message = "Satan Elite";
    private String phone = "666 1337";
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
    public void saveData() {

    }

    @Override
    public void sendMessage() {
       //sendMessageService
    }

    @Override
    public void phoneCall() {

    }
}
