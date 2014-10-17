package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import se.chalmers.katla.R;
import se.chalmers.katla.katlaTextToSpeech.IKatlaTextToSpeech;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechFactory;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechParameters;
import se.chalmers.katla.model.IKatla;
import se.chalmers.katla.model.Katla;

import static android.view.GestureDetector.SimpleOnGestureListener;

public class SendMessage extends Activity implements GestureDetector.OnGestureListener{

    private Button sendBtn, callNbrButton;
    private EditText phoneNumber;
    private EditText textMessage;
    private IKatla katlaInstance;
    private TextView countField;

    private IKatlaTextToSpeech ktts;

    private final int MAX_SMS_LENGTH = 160;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        katlaInstance = Katla.getInstance();
        ktts = KatlaTextToSpeechFactory.createKatlaTextToSpeech(getApplicationContext());

        setContentView(R.layout.activity_send_message);
       // gestureDetector = new GestureDetector(this, new MyGestureListener(this, new Intent(SendMessage.this, SwipeMainActivity.class)));
        mDetector = new GestureDetectorCompat(this,this);
        sendBtn = (Button) findViewById(R.id.sendButton);
        phoneNumber = (EditText) findViewById(R.id.contact);
        textMessage = (EditText) findViewById(R.id.textMessage);
        countField = (TextView) findViewById(R.id.wordCountField);
        textMessage.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                countField.setText((charSequence.length()/MAX_SMS_LENGTH + 1) * MAX_SMS_LENGTH -
                        charSequence.length() + "/" + (charSequence.length()/MAX_SMS_LENGTH + 1));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpeakPress();
            }
        });

        findViewById(R.id.sendMessageListenButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpeakPress();
            }
        });

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

        findViewById(R.id.sendMessageToCompositeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toComposeView();
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


    private void onSpeakPress() {
        if(ktts.readyToUse()) {
            // QUEUE_ADD adds this speak to a queue, QUEUE_FLUSH removes everything from the queue
            // and speaks your message.
            ktts.speak(textMessage.getText().toString(), KatlaTextToSpeechParameters.QUEUE_FLUSH, null);
        } else {
            Toast.makeText(getApplicationContext(), "Text to speech unavailable at this time",
                    Toast.LENGTH_SHORT).show();
        }

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
        katlaInstance.setPhone(contactNumber);
        katlaInstance.setMessage(message);

        if (katlaInstance.sendMessage()) {
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * gets the contact number specified by the user in the view
     * @return a string with the contact number
     */
    private String getContactNumber(){
        return phoneNumber.getText().toString();
    }

    /**
     * gets the message to be sent from the view
     * @return the message to be sent
     */
    private String getMessage(){
        return textMessage.getText().toString();
    }

    /**
     * Sets the specified String as the string in textMessage field.
     * @param s the String to be set as message.
     */
    private void setTextMessage(String s) {
        textMessage.setText(s);
    }

    /**
     * Sets the specified String as the phone number.
     * @param s the String to be set as phone number.
     */
    private void setContactNumber(String s) {
        phoneNumber.setText(s);
    }

    public void toComposeView() {
        // Start compose activity.
    }

    @Override
    protected void onPause() {
        katlaInstance.setMessage(getMessage());
        katlaInstance.setPhone(getContactNumber());
        ktts.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        katlaInstance = null;
        ktts.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        this.setTextMessage(katlaInstance.getMessage());
        this.setContactNumber(katlaInstance.getPhone());
        super.onResume();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v1, float v2) {
        float sensitvity = 50;
        if((e1.getY() - e2.getY()) > sensitvity){
            startActivity(new Intent(SendMessage.this, SwipeMainActivity.class));
            return true;
        }
        return false;
    }
}
