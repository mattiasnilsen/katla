package se.chalmers.katla.test.modelTests;

import android.content.res.Resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowApplication;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import se.chalmers.katla.model.Category;
import se.chalmers.katla.model.CompositesXmlParser;
import se.chalmers.katla.test.RobolectricKatlaTestRunner;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Mattias on 2014-10-09.
 */
@RunWith(RobolectricKatlaTestRunner.class)
public class TestCompositeXmlParser {

    InputStream in = null;

    @Before
    public void init() {
        ShadowApplication shadowApplication = Robolectric.getShadowApplication();
        Resources resources = shadowApplication.getResources();
        in = resources.openRawResource(resources.getIdentifier("compositestest", "raw", shadowApplication.getPackageName()));
    }

    @Test
    public void testReadingComposites() throws CompositesXmlParser.ParseException {
        CompositesXmlParser parser = new CompositesXmlParser();
        List<Category> categories = null;
        try {
            categories = parser.parse(in);
        } catch(XmlPullParserException e) {

        } catch(IOException e) {

        }
       // assertTrue(categories != null);
    }

    @After
    public void cleanUp() {

    }
}
