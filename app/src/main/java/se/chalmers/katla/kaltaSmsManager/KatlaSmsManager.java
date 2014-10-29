package se.chalmers.katla.kaltaSmsManager;

import android.app.PendingIntent;
import android.telephony.SmsManager;

import java.util.ArrayList;

/**
 * Created by Joel on 10/10/2014.
 */
public class KatlaSmsManager implements IKatlaSmsManager{
    private final int MAX_SMS_LENGTH = 160;

    SmsManager manager;
    public KatlaSmsManager() {
       manager = SmsManager.getDefault();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendTextMessage(String destinationAddress, String scAddress, String text, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        if(text.length() <= MAX_SMS_LENGTH) {
            manager.sendTextMessage(destinationAddress, scAddress, text, sentIntent, deliveryIntent);
        } else {
            ArrayList<String> messageList = divideMessage(text);
            if (messageList!= null) {
                for(String mess: messageList){
                    manager.sendTextMessage(destinationAddress, scAddress, mess, sentIntent, deliveryIntent);
                    // sendMultipartTextMessage seems to strangely not be working properly.
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendTextMessage(String destinationAddress, String scAddress, String text) {
        sendTextMessage(destinationAddress, scAddress, text, null, null);
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

    /**
     * Divides a long message into 160 character length chunks.
     * @param message the long message to divide
     * @return an arrayList containing the parts of the long message.
     */
    private ArrayList<String> divideMessage(String message){
        ArrayList<String> list = new ArrayList<String>();
        int index = 0;
        while (index<message.length()){
            list.add(message.substring(index, Math.min(index + MAX_SMS_LENGTH, message.length())));
            index += MAX_SMS_LENGTH;
        }

        return list;
    }
}
