package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import se.chalmers.katla.R;


public class SendMessage extends Activity {
    SmsManager smsManager = SmsManager.getDefault();
    Button sendBtn, callNbrButton;
    EditText phoneNumber;
    EditText textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        sendBtn = (Button) findViewById(R.id.sendButton);
        phoneNumber = (EditText) findViewById(R.id.contact);
        textMessage = (EditText) findViewById(R.id.textMessage);

        // Button to call the number in phoneNumber
        callNbrButton = (Button) findViewById(R.id.call_button);
        callNbrButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Call current number in phoneNumber textbox
             */
            @Override
            public void onClick(View v) {
                Intent callNbrIntent = new Intent(Intent.ACTION_CALL);
                // According to documentation ACTION_CALL can not call emergancy numbers?
                callNbrIntent.setData(Uri.parse("tel:" + phoneNumber.getText()));
                startActivity(callNbrIntent);
            }
        });



        sendBtn.setOnClickListener( new View.OnClickListener(){
            /**
             * specifies what happens when send button is clicked.
             * @param v the view of send button.
             */
            public void onClick(View v){

                sendMessage(getMessage(),getContactNumber());
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sends message to contact number
     * @param message the message written in the view
     * @param contactNumber the contact number specified in the view
     */
    public void sendMessage(String message, String contactNumber){
        Log.i("Send SMS", "");

        try {
            smsManager=SmsManager.getDefault();
            if(smsManager != null){
                smsManager.sendTextMessage(contactNumber, null, message, null, null );
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            Log.e("Stack trace for failure", Log.getStackTraceString(e));
        }




    }

    /**
     * gets the contact number specified by the user in the view
     * @return a string with the contact number
     */
    public String getContactNumber(){
            return phoneNumber.getText().toString();

    }

    /**
     * gets the message to be sent from the view
     * @return the message to be sent
     */
    public String getMessage(){
        return textMessage.getText().toString();
    }
}
