package se.chalmers.katla.test.ActivityTest;

import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowSmsManager;
import org.robolectric.util.ActivityController;

import se.chalmers.katla.R;
import se.chalmers.katla.activities.SendMessage;
import se.chalmers.katla.activities.ComposeActivity;
import se.chalmers.katla.model.Katla;
import se.chalmers.katla.test.RobolectricKatlaTestRunner;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Joel on 14/10/2014.
 */
@RunWith(RobolectricKatlaTestRunner.class)
public class ViewMessageTest {

    private final ActivityController<SendMessage> controller = buildActivity(SendMessage.class);
    @Test
    public void removeWordTest() throws AssertionError{
        SendMessage stt = controller.create().start().resume().get();

        TextView mainText = (TextView)stt.findViewById(R.id.speechToTextMainText);
        mainText.setText("Hej! Välkommen! Det sista ordet här ska tas bort");
        stt.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals("Hej! Välkommen! Det sista ordet här ska tas "));

        mainText.setText("Den sista siffer grejen här ska tas bort 818123 1238123");
        stt.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals("Den sista siffer grejen här ska tas bort 818123 "));

        mainText.setText("Det sista skiljetecknet här ska bort!");
        stt.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals("Det sista skiljetecknet här ska bort"));

        mainText.setText(".,.,!(!#%!%!81237/==!#");
        stt.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals(".,.,!(!#%!%!81237/==!"));

        mainText.setText("");
        stt.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals(""));

        mainText.setText("Ta bort det sista skiljetecknet och de två sista orden!");
        stt.findViewById(R.id.removeBtnSTT).performClick();
        stt.findViewById(R.id.removeBtnSTT).performClick();
        stt.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals("Ta bort det sista skiljetecknet och de två "));

        mainText.setText("Ta");
        stt.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals(""));
    }

    @Test
    public void testToCompositeButton() throws AssertionError {
        SendMessage stt = controller.create().start().resume().get();

        Button btn = (Button)stt.findViewById(R.id.composeBtnSTT);

        btn.performClick();
        Intent expectedIntent = new Intent(stt, ComposeActivity.class);
        assertTrue(shadowOf(stt).getNextStartedActivity().equals(expectedIntent));
    }

    @Test
    public void testSendMessageButton() {
        SendMessage stt = controller.create().start().resume().get();
        Katla katla = Katla.getInstance();

        TextView textView = (TextView) stt.findViewById(R.id.speechToTextMainText);
        String s = "Hej nu testar vi at skicka ett sms, får se om det funkar";
        textView.setText(s);
        String number = katla.getMessage();

        stt.findViewById(R.id.sendBtnSTT).performClick();

        ShadowSmsManager shadowSmsManager = shadowOf(SmsManager.getDefault());
        ShadowSmsManager.TextSmsParams lastSendTextMessageParams = shadowSmsManager.getLastSentTextMessageParams();

        assertEquals(s, lastSendTextMessageParams.getText());
        assertEquals(number, lastSendTextMessageParams.getDestinationAddress());
    }
}
