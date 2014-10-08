package se.chalmers.katla.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReciever extends BroadcastReceiver {
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

                    //show alert
                    Toast.makeText(context, "Message from: " + msgSource + ": " + msgBody, Toast.LENGTH_LONG).show();

                    //TODO something with the message
                    //Add sound, notification connect with homescreen mm.

                }
            }//bundle is null
        } catch (Exception e){
            Log.e("smsReciever", "Exception smsReciever");
        }
    }
}
