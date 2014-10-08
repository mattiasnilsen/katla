package se.chalmers.katla.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anton on 2014-10-02.
 */
public class Katla implements IKatla {

    private static Katla ourInstance;
    private String message = "";
    private String phone = "";
    private List<ICategory> categories;

    public static Katla getInstance() {
        if(ourInstance == null) {
            ourInstance = new Katla();
        }
        return ourInstance;
    }


    private Katla() {
        categories = new ArrayList<ICategory>();
        CompositesXmlParser parser = new CompositesXmlParser();

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("res/raw/composites.xml");
        System.out.println(in);
        try {
            System.out.println(in.available());
            parser.parse(in);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Iterator<ICategory> getCategories() {
        return null;
    }

    @Override
    public void addCategory(ICategory category) {

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

    }
}
