package se.chalmers.katla.test.ActivityTest;

import android.widget.TextView;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.ActivityController;

import se.chalmers.katla.R;
import se.chalmers.katla.activities.SpeechToText;
import se.chalmers.katla.test.RobolectricKatlaTestRunner;


import static junit.framework.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;

/**
 * Created by Joel on 14/10/2014.
 */
@RunWith(RobolectricKatlaTestRunner.class)
public class SpeechToTextTest {

    private final ActivityController<SpeechToText> controller = buildActivity(SpeechToText.class);
    @Test
    public void removeWordTest() throws AssertionError{
        SpeechToText stt = controller.create().start().resume().get();

        TextView mainText = (TextView)stt.findViewById(R.id.speechToTextMainText);
        mainText.setText("Hej! Välkommen! Det sista ordet här ska tas bort");
        stt.findViewById(R.id.speechToTextRemoveButton).performClick();
        assertTrue(mainText.getText().equals("Hej! Välkommen! Det sista ordet här ska tas "));

        mainText.setText("Den sista siffer grejen här ska tas bort 818123 1238123");
        stt.findViewById(R.id.speechToTextRemoveButton).performClick();
        assertTrue(mainText.getText().equals("Den sista siffer grejen här ska tas bort 818123 "));

        mainText.setText("Det sista skiljetecknet här ska bort!");
        stt.findViewById(R.id.speechToTextRemoveButton).performClick();
        assertTrue(mainText.getText().equals("Det sista skiljetecknet här ska bort"));

        mainText.setText(".,.,!(!#%!%!81237/==!#");
        stt.findViewById(R.id.speechToTextRemoveButton).performClick();
        assertTrue(mainText.getText().equals(".,.,!(!#%!%!81237/==!"));

        mainText.setText("");
        stt.findViewById(R.id.speechToTextRemoveButton).performClick();
        assertTrue(mainText.getText().equals(""));
    }

}
