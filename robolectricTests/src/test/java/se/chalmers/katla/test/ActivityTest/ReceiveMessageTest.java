package se.chalmers.katla.test.ActivityTest;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.ActivityController;

import se.chalmers.katla.R;
import se.chalmers.katla.activities.ReceiveMessage;
import se.chalmers.katla.activities.SendMessage;
import se.chalmers.katla.test.RobolectricKatlaTestRunner;

import static junit.framework.Assert.assertTrue;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by Joel on 22/10/2014.
 */
@RunWith(RobolectricKatlaTestRunner.class)
public class ReceiveMessageTest {

    private final ActivityController<ReceiveMessage> controller = buildActivity(ReceiveMessage.class);

    @Test
    public void toCompositeButton() {
       /* ReceiveMessage rm = controller.create().start().resume().get();


        rm.findViewById(R.id.replyBtnRM).performClick();;

        Intent expectedIntent = new Intent(rm, SendMessage.class);
        assertTrue(shadowOf(rm).getNextStartedActivity().equals(expectedIntent));
        */
    }
}
