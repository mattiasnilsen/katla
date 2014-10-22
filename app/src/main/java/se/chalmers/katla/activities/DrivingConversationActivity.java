package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.katla.R;
import se.chalmers.katla.util.ActivitySwipeDetector;
import se.chalmers.katla.util.SwipeInterface;

/**
 * The Activity that looks at a conversation one sms at a time.
 */
public class DrivingConversationActivity extends Activity implements SwipeInterface{
ArrayList<String> allSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivingconversation);
        Intent myIntent = getIntent();
        String nameOfConversation = myIntent.getStringExtra("nameOfConversation");
        // Get the id for the conversation that is opened.
        int conversation_id = myIntent.getIntExtra("id",0);

        Uri uri = Uri.parse("content://sms/");
        ContentResolver contentResolver = getContentResolver();
        String searchFor = "thread_id = " + conversation_id;
        // Create a cursor that searches for all sms with that conversation id
        Cursor convCursor = contentResolver.query(uri, null, searchFor, null, null);

        // Create the SwipeDetector that handles moving backwards text by text
        ActivitySwipeDetector swipeDetector = new ActivitySwipeDetector(this);
        RelativeLayout swipeLayout = (RelativeLayout)findViewById(R.id.currentSmsLayout);
        swipeLayout.setOnTouchListener(swipeDetector);

        // Create the ArrayList that holds all sms in this conversation
        allSms = new ArrayList<String>();


        // Get the body text for each sms in the conversation and save it to the list
        int bodyIndex = convCursor.getColumnIndex("body");
        for(convCursor.moveToLast() ;!convCursor.isBeforeFirst(); convCursor.moveToPrevious()) {
            String j = convCursor.getString(bodyIndex);
            allSms.add(0,j);
        }
        // Create the textView and make sure it's scrollable
        TextView currentSms = (TextView)findViewById(R.id.currentSmsView);
        currentSms.setMovementMethod(new ScrollingMovementMethod());
        // Show the latest sms
        currentSms.setText(allSms.get(0));
        TextView currentContact = (TextView)findViewById(R.id.conversationContactText);
        currentContact.setText(nameOfConversation);



    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_ndconversation, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bottom2top(View v) {

    }

    @Override
    public void left2right(View v) {
        allSms.remove(0);
        TextView currentSms = (TextView)v.findViewById(R.id.currentSmsView);
        currentSms.setText(allSms.get(0));
    }

    @Override
    public void right2left(View v) {

    }

    @Override
    public void top2bottom(View v) {

    }
}
