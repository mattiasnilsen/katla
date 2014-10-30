package se.chalmers.katla.test.ActivityTest;

import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowSmsManager;
import org.robolectric.util.ActivityController;

import se.chalmers.katla.R;
import se.chalmers.katla.activities.SendMessage;
import se.chalmers.katla.activities.ComposeActivity;
import se.chalmers.katla.activities.ContactService;
import se.chalmers.katla.model.Katla;
import se.chalmers.katla.test.RobolectricKatlaTestRunner;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Joel on 14/10/2014.
 */
@RunWith(RobolectricKatlaTestRunner.class)
public class SendMessageText {

    private final ActivityController<SendMessage> controller = buildActivity(SendMessage.class);
    @Test
    public void removeWordTest() throws AssertionError{
        SendMessage sm = controller.create().start().resume().get();

        TextView mainText = (TextView)sm.findViewById(R.id.speechToTextMainText);
        mainText.setText("Hej! Välkommen! Det sista ordet här ska tas bort");
        sm.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals("Hej! Välkommen! Det sista ordet här ska tas "));

        mainText.setText("Den sista siffer grejen här ska tas bort 818123 1238123");
        sm.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals("Den sista siffer grejen här ska tas bort 818123 "));

        mainText.setText("Det sista skiljetecknet här ska bort!");
        sm.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals("Det sista skiljetecknet här ska bort"));

        mainText.setText(".,.,!(!#%!%!81237/==!#");
        sm.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals(".,.,!(!#%!%!81237/==!"));

        mainText.setText("");
        sm.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals(""));

        mainText.setText("Ta bort det sista skiljetecknet och de två sista orden!");
        sm.findViewById(R.id.removeBtnSTT).performClick();
        sm.findViewById(R.id.removeBtnSTT).performClick();
        sm.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals("Ta bort det sista skiljetecknet och de två "));

        mainText.setText("Ta");
        sm.findViewById(R.id.removeBtnSTT).performClick();
        assertTrue(mainText.getText().equals(""));
    }

    @Test
    public void testToCompositeButton() throws AssertionError {
        SendMessage sm = controller.create().start().resume().get();

        sm.findViewById(R.id.composeBtnSTT).performClick();;
        Intent expectedIntent = new Intent(sm, ComposeActivity.class);
        assertTrue(shadowOf(sm).getNextStartedActivity().equals(expectedIntent));
    }

    @Test//Can't be tested with current robolectric version.
    public void testSendMessageButton() {
        /*SendMessage stt = controller.create().start().resume().get();
        Katla katla = Katla.getInstance();

        TextView textView = (TextView) stt.findViewById(R.id.speechToTextMainText);
        String s = "Hej nu testar vi at skicka ett sms, får se om det funkar";
        textView.setText(s);
        String number = katla.getPhone();

        stt.findViewById(R.id.sendBtnSTT).performClick();

        ShadowSmsManager shadowSmsManager = shadowOf(SmsManager.getDefault());
        ShadowSmsManager.TextSmsParams lastSendTextMessageParams = shadowSmsManager.getLastSentTextMessageParams();

        assertEquals(s, lastSendTextMessageParams.getText());
        assertEquals(number, lastSendTextMessageParams.getDestinationAddress());
        */
    }

    @Test
    public void testToContactChooser() {
        SendMessage sm = controller.create().start().resume().get();

        Intent expectedIntent = new Intent(sm, ContactService.class);

        sm.findViewById(R.id.contactLayoutSTT).performClick();

        assertTrue(shadowOf(sm).getNextStartedActivity().equals(expectedIntent));

    }
}
