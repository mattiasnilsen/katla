package se.chalmers.katla.kaltaSmsManager;

import android.app.PendingIntent;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by Joel on 10/10/2014.
 */
public class KatlaSmsManager implements IKatlaSmsManager{
    SmsManager manager;
    public KatlaSmsManager() {
       manager = SmsManager.getDefault();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        manager.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendTextMessage(String destinationAddress, String scAddress, String text) {
        manager.sendTextMessage(destinationAddress, scAddress, text, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts, ArrayList<PendingIntent> sentIntent, ArrayList<PendingIntent> deliveryIntent) {
        manager.sendMultipartTextMessage(destinationAddress, scAddress, parts, sentIntent, deliveryIntent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMultipartTextMessage(String destinationAddress, String scAddress, ArrayList<String> parts) {
        manager.sendMultipartTextMessage(destinationAddress, scAddress, parts, null, null);
    }
}
