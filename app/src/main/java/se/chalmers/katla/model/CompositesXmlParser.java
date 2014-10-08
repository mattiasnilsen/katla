package se.chalmers.katla.model;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.katla.R;

/**
 * Created by Mattias on 2014-10-06.
 */
public class CompositesXmlParser {

    private final String COMPOSITE_TAG = "composite";
    private final String CATEGORY_TAG = "category";

    public List parse(InputStream in)  throws XmlPullParserException, IOException{
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
           return readComposites(parser);
        } finally {
            in.close();
        }
    }

    private List readComposites(XmlPullParser parser) throws XmlPullParserException, IOException {
        List categories = new ArrayList<IComposite>();
        int eventType = parser.getEventType();

        ICategory currentCategory = null;
        IComposite currentComposite = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                if(parser.getName().equals(CATEGORY_TAG)) {
                    if(currentCategory != null) {
                        //TODO throw exception?
                    } else {
                        currentCategory = new Category(parser.getAttributeValue(0));
                    }
                } else if(parser.getName().equals(COMPOSITE_TAG)) {
                    currentComposite = new Composite();
                }
            } else if(eventType == XmlPullParser.END_TAG) {
                if(parser.getName().equals(COMPOSITE_TAG) && currentComposite != null) {
                    currentCategory.addComposite(currentComposite);
                    currentComposite = null;
                } else if(parser.getName().equals(CATEGORY_TAG)) {
                    categories.add(currentCategory);
                    currentCategory = null;
                }
            } else if(eventType == XmlPullParser.TEXT) {
                if(currentComposite != null) {
                    currentComposite.setBaseText(parser.getText());
                }
            }
            eventType = parser.next();
        }

        return categories;
    }
}
