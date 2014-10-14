package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import se.chalmers.katla.R;

public class ContactService extends Activity {
    private ListView contactsListView;
    private String search = "+46725164141";
    private ContactService contactService = this;
    private ContacsCursorAdapter contacsCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_service);

        ContentResolver contentResolver = getContentResolver();
        // Create the projection, i.e. the values we want to query from the database
        String[] projection = {ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME };
        // The URL for the conversations

        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(search));
        // Create a cursor, i.e. a pointer to a row in the database
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        // Create the adapter that maps data from the cursor onto the view

        contacsCursorAdapter = new ContacsCursorAdapter(contactService,cursor,0);
        // Get the listView and set the above adapter
        contactsListView = (ListView)findViewById(R.id.listView3);
        contactsListView.setAdapter(contacsCursorAdapter);


        Button searchBtn = (Button)findViewById(R.id.searchBtnCS);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.contactInput);
                search = "" + input.getText();
                ContentResolver contentResolver = getContentResolver();
                // Create the projection, i.e. the values we want to query from the database
                String[] projection = {BaseColumns._ID,
                        ContactsContract.PhoneLookup.DISPLAY_NAME };
                // The URL for the conversations

                Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(search));
                Cursor cursor = contentResolver.query(uri, projection,null, null, null);
                contacsCursorAdapter.swapCursor(cursor);
            }
        });

        EditText input = (EditText)findViewById(R.id.contactInput);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                EditText input = (EditText)findViewById(R.id.contactInput);
                search = "" + input.getText();
                ContentResolver contentResolver = getContentResolver();
                // Create the projection, i.e. the values we want to query from the database
                String[] projection = {ContactsContract.Contacts._ID,
                        ContactsContract.PhoneLookup.DISPLAY_NAME };
                // The URL for the conversations


                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(search));
                // Create a cursor, i.e. a pointer to a row in the database
                Cursor cursor = contentResolver.query(uri, projection, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
                contacsCursorAdapter.swapCursor(cursor);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
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

    private class ContacsCursorAdapter extends android.widget.CursorAdapter {
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
            View v = inflater.inflate(R.layout.contacts_list_item, parent, false);
            return v;
        }

        @Override
        /**
         * Binds the data to the correct views.
         */
        public void bindView(View view, Context context, Cursor cursor) {

            TextView namn = (TextView)findViewById(R.id.listItemText);
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            if (namn != null) {
                namn.setText(name + cursor.getCount());
            }
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
