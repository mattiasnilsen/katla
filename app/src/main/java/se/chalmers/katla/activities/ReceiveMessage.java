package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import se.chalmers.katla.R;
import se.chalmers.katla.katlaTextToSpeech.IKatlaTextToSpeech;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechFactory;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechParameters;
import se.chalmers.katla.model.IKatla;
import se.chalmers.katla.model.Katla;
import se.chalmers.katla.util.ActivitySwipeDetector;
import se.chalmers.katla.util.SwipeInterface;

public class ReceiveMessage extends Activity implements SwipeInterface {
    private IKatla model;
    private ArrayList<String> allSms;
    private IKatlaTextToSpeech ktts;
    private TextView currentSms;
    private ScrollView scrollView;
    private int conversationID;
    private int currentTextIndex;
    private ActivitySwipeDetector swipeDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);
        Intent myIntent = getIntent();
        // Get the id for the conversation that is opened.
        conversationID = myIntent.getIntExtra("id",0);



        currentSms = (TextView)findViewById(R.id.textMessageRM);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        // Create the SwipeDetector that handles moving backwards text by text
        swipeDetector = new ActivitySwipeDetector(this);
        currentSms.setOnTouchListener(swipeDetector);
        scrollView.setOnTouchListener(swipeDetector);

        // Create the ArrayList that holds all sms in this conversation
        allSms = new ArrayList<String>();



        // Create the textView and make sure it's scrollable

        currentSms.setMovementMethod(new ScrollingMovementMethod());
        // Show the latest sms
        //TextView currentContact = (TextView)findViewById(R.id.conversationContactText);
        //currentContact.setText(nameOfConversation);
        //====================================================================


        model = Katla.getInstance();
        ktts = KatlaTextToSpeechFactory.createKatlaTextToSpeech(getApplicationContext());
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;



        GridLayout btnBar = (GridLayout)findViewById(R.id.buttonBarRM);
        LayoutParams params = btnBar.getLayoutParams();
        //params.height = size.y;
        params.width = size.x;
        params.height = size.x/3;



        ImageButton callBtn = (ImageButton)findViewById(R.id.callBtnRM);
        ImageButton replyBtn = (ImageButton)findViewById(R.id.replyBtnRM);
        ImageButton ttsBtn = (ImageButton)findViewById(R.id.textToSpeechBtnRM);
        LinearLayout contactLayout = (LinearLayout)findViewById(R.id.contactLayoutRM);

        params = callBtn.getLayoutParams();
        params.width = size.x/3;
        params.height = size.x/3;
        params = replyBtn.getLayoutParams();
        params.width = size.x/3;
        params.height = size.x/3;
        params = ttsBtn.getLayoutParams();
        params.width = size.x/3;
        params.height = size.x/3;
        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speechToTextIntent = new Intent(ReceiveMessage.this, SendMessage.class);

                startActivity(speechToTextIntent);
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                // According to documentation ACTION_CALL can not call emergency numbers?
                intent.setData(Uri.parse("tel:" + model.getPhone()));
                startActivity(intent);
            }
        });

        ttsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpeakPress();
            }
        });
        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactServiceIntent = new Intent(ReceiveMessage.this, ContactService.class);

                startActivity(contactServiceIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.receive_message, menu);
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

    @Override
    protected void onResume() {
        super.onResume();
        TextView temp = (TextView)findViewById(R.id.contactRM);
        temp.setText(model.getContact());
        temp = (TextView)findViewById(R.id.phoneRM);
        temp.setText(model.getPhone());

        Uri uri = Uri.parse("content://sms/");
        ContentResolver contentResolver = getContentResolver();
        String searchFor = "thread_id = " + conversationID;
        // Create a cursor that searches for all sms with that conversation id
        Cursor convCursor = contentResolver.query(uri, null, searchFor, null, null);

        // Get the body text for each sms in the conversation and save it to the list
        int bodyIndex = convCursor.getColumnIndex("body");
        for(convCursor.moveToLast() ;!convCursor.isBeforeFirst(); convCursor.moveToPrevious()) {
            String j = convCursor.getString(bodyIndex);
            allSms.add(0,j);
        }
        currentTextIndex = 0;
        currentSms.setText(allSms.get(currentTextIndex));
    }

    /**
     * Handles what happens when you want text to be spoken.
     */
    private void onSpeakPress() {
        if(ktts.readyToUse()) {
            // QUEUE_ADD adds this speak to a queue, QUEUE_FLUSH removes everything from the queue
            // and speaks your message.
            TextView text = (TextView)findViewById(R.id.textMessageRM);
            ktts.speak(text.getText().toString(), KatlaTextToSpeechParameters.QUEUE_FLUSH, null);
        } else {
            Toast.makeText(getApplicationContext(), "Text to speech unavailable at this time",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void bottom2top(View v) {

    }

    @Override
    public void left2right(View v) {
        if(currentTextIndex<=(allSms.size()-2)) {
            currentTextIndex++;
            currentSms.setText(allSms.get(currentTextIndex));
        }
    }

    @Override
    public void right2left(View v) {
        if(currentTextIndex>=1) {
            currentTextIndex--;
            currentSms.setText(allSms.get(currentTextIndex));
        }
    }

    @Override
    public void top2bottom(View v) {

    }
}
