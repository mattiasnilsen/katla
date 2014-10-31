package se.chalmers.katla.model;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.chalmers.katla.agaListenerService.AgaListener;
import se.chalmers.katla.eventBus.EventBus;
import se.chalmers.katla.kaltaSmsManager.IKatlaSmsManager;
import se.chalmers.katla.kaltaSmsManager.KatlaSmsManagerFactory;

/**
 * Created by Anton on 2014-10-02.
 */
public class Katla implements IKatla {

    private final String COMPOSITES_XML_FILE = "res/raw/composites.xml";

    private static Katla ourInstance;
    private String message = "Alla vill till himlen men få vill ju dö hgfghf fhg hgf hgf hgf ghf hgf hgf hgf hgf hgf hgf hgf hgf hgf hgf hgf hgf ghf hgf hgf hgffghgf hg fhgf hg fhgf hg fhgf hfjhgf hgfkghfkf ufk h khg khg g hej alla fina getter!";
    private String phone = "0707833811";
    private String contact = "Satan Elite";
    private AgaListener agaListener;

    private int distractionLevel;

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
        agaListener = new AgaListener();

        EventBus.getInstance().registerListener(this);

    }

    @Override
    public void loadComposites() throws CompositesXmlParser.ParseException{
        CompositesXmlParser parser = new CompositesXmlParser();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(COMPOSITES_XML_FILE);

        try {
            categories = parser.parse(in);
        } catch (IOException e) {
            throw new CompositesXmlParser.ParseException("Can't load file");
        } catch (XmlPullParserException e) {
            throw new CompositesXmlParser.ParseException(e.getMessage());
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

        try {
            if (manager != null) {
                manager.sendTextMessage(getPhone(), null, message);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Stack trace for failure" + e.getStackTrace());
            return false;
        }
        return true;
    }

    @Override
    public void phoneCall() {


    }

    @Override
    public void receiveEvent(String s, Object o) {
        if (s.equals("Driver distraction changed")) {
            this.distractionLevel = (Integer)o;
        }
    }

    @Override
    public int getDistractionLevel() {
        return this.distractionLevel;
    }

    @Override
    public void readyForNewMessage() {
        this.message = "";
        this.contact = "";
        this.phone = "";
    }
}

