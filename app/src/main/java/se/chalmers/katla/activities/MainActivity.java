package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import se.chalmers.katla.R;

/**
 * @author Erik Norlander
 * Created 2014-09-30
 * Launcher activity for the Katla aplication.
 */
public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initiate the Button to create an SMS.
        final Button createSMSButton = (Button)findViewById(R.id.button_createSMS);

        // When button is pressed, start create SMS activity.
        createSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendMessageIntent = new Intent(MainActivity.this, SendMessage.class);

                startActivity(sendMessageIntent);
            }
        });




        // Get the SMS-conversations
        ContentResolver contentResolver = getContentResolver();
        // Create the projection, i.e. the values we want to query from the database
        final String[] projection = {"body","_id","thread_id","address","date"};
        // The URL for the conversations
        Uri uri = Uri.parse("content://mms-sms/conversations/");
        // Create a cursor, i.e. a pointer to a row in the database
        Cursor cursor = contentResolver.query(uri, projection, null, null, "date ASC");

        // Move to the first row
        if(cursor.moveToFirst()) {

            // Get the indexes for the values we want for the conversationlist
            int bodyIndex = cursor.getColumnIndex(projection[0]);
            int _idIndex = cursor.getColumnIndex(projection[1]);
            int thread_idIndex = cursor.getColumnIndex(projection[2]);
            int addressIndex = cursor.getColumnIndex(projection[3]);
            int dateIndex = cursor.getColumnIndex(projection[4]);
             do {
                 // Get the specific values
                 String body = cursor.getString(bodyIndex);
                 long id = cursor.getLong(_idIndex);
                 long threadId = cursor.getLong(thread_idIndex);
                 String address = cursor.getString(addressIndex);
                 long date = cursor.getLong(dateIndex);


             } while (cursor.moveToNext());
        }
        // Close cursor
        cursor.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
