package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.text.Html;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import se.chalmers.katla.R;


/**
 * The Launcher activity of Katla.
 * @author Erik Norlander
 * Created 2014-09-30
 * Launcher activity for the Katla aplication.
 */
public class MainActivity extends Activity {
    private ListView conversationsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        //Temp button to get to another view
        final Button receiveSMSButton = (Button)findViewById(R.id.tempBtn);

        // When button is pressed, start create SMS activity.
        receiveSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receiveMessageIntent = new Intent(MainActivity.this, ReceiveMessage.class);

                startActivity(receiveMessageIntent);
            }
        });
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
        final String[] projection = {"body","_id","thread_id","address","date","person"};
        // The URL for the conversations
        Uri uri = Uri.parse("content://mms-sms/conversations/");
        // Create a cursor, i.e. a pointer to a row in the database
        Cursor cursor = contentResolver.query(uri, projection, null, null, "date DESC");
        // Create the adapter that maps data from the cursor onto the view
        ContacsCursorAdapter contacsCursorAdapter = new ContacsCursorAdapter(this,cursor,0);
        // Get the listView and set the above adapter
        conversationsListView = (ListView)findViewById(R.id.conversationListView);
        conversationsListView.setAdapter(contacsCursorAdapter);


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

    /**
     * A class that maps the last text in a conversation and a contact to the conversationslistview.
     */
    private class ContacsCursorAdapter extends CursorAdapter {
        /**
         * Same constructor as CursorAdapter.
         * @param context
         * @param c
         * @param flags
         */
        public ContacsCursorAdapter(Context context, Cursor c, int flags) {
            super(context,c,flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.conversation_list_item, parent, false);

            return v;
        }

        @Override
        /**
         * Binds the data to the correct views.
         */
        public void bindView(View view, Context context, final Cursor cursor) {


            int addressIndex = cursor.getColumnIndex("address");
            final String number = cursor.getString(addressIndex);
            String nameOfContact = getContactDisplayNameByNumber(number);
            final String nameToBeUsed;
            if (nameOfContact != null) {
                // If the contact doesnt exist use the phone number:
                nameToBeUsed = nameOfContact;
            } else {
                nameToBeUsed = number;
            }


            int bodyIndex = cursor.getColumnIndex("body");
            String body = cursor.getString(bodyIndex);
            int nbrOfCharShown = 20;
            String showText;
            if(body.length() > nbrOfCharShown) {
                //showText = body.substring(0,nbrOfCharShown) + "...";
                showText = body;
            } else {
                showText = body;
            }
            //showText = showText.replace("\n", " ");


            TextView conversationText = (TextView) view.findViewById(R.id.conversationTextField);
            TextView contact = (TextView) view.findViewById(R.id.drivingContactText);
            //conversationText.setText(Html.fromHtml("<b>" + nameToBeUsed + "</b>" + "<br />" + showText));
            conversationText.setText(showText);
            contact.setText(nameToBeUsed);
            //conversationText.setTextSize(38f);

            conversationText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showConversationIntent = new Intent(MainActivity.this, DrivingConversationActivity.class);
                    showConversationIntent.putExtra("phoneNumber", number);
                    int id_index = cursor.getColumnIndex("thread_id");
                    int id = cursor.getInt(id_index);
                    showConversationIntent.putExtra("id", id);
                    showConversationIntent.putExtra("nameOfConversation",nameToBeUsed);
                    startActivity(showConversationIntent);
                }
            });


        }

        private String getContactDisplayNameByNumber(String number) {

            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            String name = null;

            ContentResolver contentResolver = getContentResolver();

            String[] projection = {BaseColumns._ID,
                    ContactsContract.PhoneLookup.DISPLAY_NAME };
            Cursor contactLookup = contentResolver.query(uri,projection, null, null, null);

            try {
                if (contactLookup != null && contactLookup.getCount() > 0) {
                    contactLookup.moveToNext();
                    name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                    //String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
                }
            } finally {
                if (contactLookup != null) {
                    contactLookup.close();
                }
            }

            return name;
        }
    }
}
