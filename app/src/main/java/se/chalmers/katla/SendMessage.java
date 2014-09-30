package se.chalmers.katla;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SendMessage extends Activity {
    SmsManager smsManager = SmsManager.getDefault();
    Button sendBtn;
    EditText phoneNumber;
    EditText textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        sendBtn = (Button) findViewById(R.id.sendButton);
        phoneNumber = (EditText) findViewById(R.id.contact);
        textMessage = (EditText) findViewById(R.id.textMessage);

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
