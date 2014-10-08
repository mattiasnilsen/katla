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
        List entries = new ArrayList();
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if(eventType == XmlPullParser.END_DOCUMENT) {
                System.out.println("End document");
            } else if(eventType == XmlPullParser.START_TAG) {
                System.out.println("Start tag "+parser.getName());
            } else if(eventType == XmlPullParser.END_TAG) {
                System.out.println("End tag "+parser.getName());
            } else if(eventType == XmlPullParser.TEXT) {
                System.out.println("Text "+parser.getText());
            }
            eventType = parser.next();
        }

        return entries;
    }
}
