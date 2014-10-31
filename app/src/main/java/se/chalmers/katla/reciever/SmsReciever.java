package se.chalmers.katla.reciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import se.chalmers.katla.R;
import se.chalmers.katla.activities.MainActivity;

public class SmsReciever extends BroadcastReceiver {
    // sets the group key
    private final static String NOTIFICATION = "notification";
    //get the SmsManager object
    final SmsManager manager = SmsManager.getDefault();

    public SmsReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //retrive a map of extended data from the intent
        Bundle extras = intent.getExtras();
        try{
            if (extras!=null){
                Object[] smsExtras = (Object[]) extras.get("pdus");

                for (Object currentObj: smsExtras){
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) currentObj);

                    //message and message source
                    String msgBody = currentMessage.getMessageBody();
                    String msgSource = currentMessage.getOriginatingAddress();

                    ContentValues values = new ContentValues();
                    values.put(Telephony.Sms.ADDRESS, msgSource);
                    values.put(Telephony.Sms.BODY, msgBody);
                    context.getContentResolver().insert(Telephony.Sms.Inbox.CONTENT_URI, values);
                    //trigger the notification
                   notification(context, msgBody, msgSource);


                }
            }//bundle is null
        } catch (Exception e){
            Log.e("smsReciever", "Exception smsReciever");
        }
    }


    private String getContact(String number){
        return number;
    }
    private String trimmedMsg(String message){
        if(message.length()<30){
            return message;
        }else{
            return message.substring(0, 30) + "...";
        }
    }
    /**
     * A method that builds and fiers the notification to the notification bar
     * @param context the context
     * @param message the message that was recieved
     * @param phoneNumber the phonenumber that the message came from
     */

    public void notification(Context context, String message, String phoneNumber){
        Counter.increment();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);


        //intent to be trigged by selecting the notification
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);

        //set strings depending on the number of unviewed messages
        String title;
        String text;
        if(Counter.getNumberOfNotifications()== 0){
            title = "New message from " + getContact(phoneNumber);
            text = trimmedMsg(message);
        }else{
            title = Counter.getNumberOfNotifications() + " new messages";
            text = "You have " + Counter.getNumberOfNotifications() + " new messages";
        }



        //set vibration pattern
        long[] pattern = {500,500,500,500,500,500,500,500,500};

        //build notification
        Notification summary = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.icon)
                .setGroupSummary(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLights(Color.GREEN, 500, 500)
                .setVibrate(pattern)
                .setGroup(NOTIFICATION)
                .build();

        notificationManager.cancelAll();
        notificationManager.notify(Counter.getNumberOfNotifications(), summary);

    }

}
