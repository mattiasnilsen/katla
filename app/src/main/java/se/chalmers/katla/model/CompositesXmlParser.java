package se.chalmers.katla.model;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
    private final String INPUT_TAG = "input";

    public static class ParseException extends Exception {
        public ParseException() { super(); }
        public ParseException(String message) { super(message); }
        public ParseException(String message, Throwable cause) { super(message, cause); }
        public ParseException(Throwable cause) { super(cause); }
    }

    public List parse(InputStream in)  throws XmlPullParserException, IOException, ParseException{
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
           return readComposites(parser);
        } finally {
            in.close();
        }
    }

    private List readComposites(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
        List categories = new ArrayList<IComposite>();
        int eventType = parser.getEventType();

        ICategory currentCategory = null;
        IComposite currentComposite = null;
        Boolean insideInputTag = false;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_TAG) {
                if(parser.getName().equals(CATEGORY_TAG)) {
                    if(currentCategory != null) {
                        throw new ParseException("XML file error: invalid syntax");
                    } else {
                        currentCategory = new Category(parser.getAttributeValue(0));
                    }
                } else if(parser.getName().equals(COMPOSITE_TAG)) {
                    currentComposite = new Composite();
                } else if(parser.getName().equals(INPUT_TAG)) {
                    insideInputTag = true;
                }
            } else if(eventType == XmlPullParser.END_TAG) {
                if(parser.getName().equals(COMPOSITE_TAG) && currentComposite != null) {
                    currentCategory.addComposite(currentComposite);
                    currentComposite = null;
                } else if(parser.getName().equals(CATEGORY_TAG)) {
                    categories.add(currentCategory);
                    currentCategory = null;
                } else if(parser.getName().equals(INPUT_TAG)) {
                    insideInputTag = false;
                }
            } else if(eventType == XmlPullParser.TEXT) {
                if(currentComposite != null) {
                    currentComposite.setBaseText(parser.getText());
                }
                if(insideInputTag) {
                    List<String> inputs = getInputTypes(parser.getText());
                    currentComposite.setInputs(inputs);
                }
            }
            eventType = parser.next();
        }

        return categories;
    }

    private List<String> getInputTypes(String input) {
        List<String> inputs = new ArrayList<String>();
        String[] inputArray = input.split(",");
        for (int i = 0; i < inputArray.length; ++i) {
            inputs.add(inputArray[i]);
        }
        return inputs;
    }
}
