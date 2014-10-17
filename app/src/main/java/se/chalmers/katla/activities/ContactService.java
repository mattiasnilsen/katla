package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import se.chalmers.katla.R;
import se.chalmers.katla.model.Katla;

public class ContactService extends Activity {
    private ContactsCursorAdapter contactsCursorAdapter;
    private Katla model = Katla.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_service);


        contactsCursorAdapter = new ContactsCursorAdapter(this,createDefaultCursor(),0);

        // Get the listView and set the above adapter
        ListView contactsListView = (ListView)findViewById(R.id.listView3);
        contactsListView.setAdapter(contactsCursorAdapter);

        //Temp btn
        Button btn = (Button)findViewById(R.id.searchBtnCS);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent receiveMessageIntent = new Intent(ContactService.this, ReceiveMessage.class);
                startActivity(receiveMessageIntent);
            }
        });

        //Implement listener for inputchange
        EditText input = (EditText)findViewById(R.id.contactInput);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //Not sure this is necessary
                contactsCursorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //Get input as it changes
                EditText input = (EditText)findViewById(R.id.contactInput);
                String searchInput = input.getText() + "";
                Cursor cursor = null;
                //SearchInput changed, make a new cursor for contactsCursorAdapter
                if(searchInput == ""){
                    //Get a cursor that shows all contacts
                    cursor = createDefaultCursor();
                }else{
                    ContentResolver contentResolver = getContentResolver();
                    String[] projection = { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME };
                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(searchInput));
                    cursor = contentResolver.query(uri, projection, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
                }

                contactsCursorAdapter.swapCursor(cursor);
                contactsCursorAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Not sure this is necessary
                contactsCursorAdapter.notifyDataSetChanged();
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

    private class ContactsCursorAdapter extends android.widget.CursorAdapter {

        public ContactsCursorAdapter(Context context, Cursor c, int flags) {
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
            Button contact = (Button)view.findViewById(R.id.listItemText);
            final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            final String phone = getContactPhoneByContactName(name);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    model.setContact(name);
                    model.setPhone(phone);
                    //TODO change view here
                }
            });
            if (contact != null && phone!= null) {
                contact.setText(name + "\n" + phone);
            } else if(contact != null){
                contact.setText(name + "\n" + "No number");
            }
        }
    }

    private String getContactPhoneByContactName(String name){
        String phone = null;
        //Create a cursor searching for Phone by Name
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, Uri.encode(name));
        String[] projection = {ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.NUMBER };
        Cursor phoneLookup = contentResolver.query(uri,projection, null, null, null);

        try {
            if (phoneLookup != null && phoneLookup.getCount() > 0) {
                phoneLookup.moveToNext();
                phone = phoneLookup.getString(phoneLookup.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
        } finally {
            if (phoneLookup != null) {
                phoneLookup.close();
            }
        }
        return phone;
    }

    private Cursor createDefaultCursor(){
        ContentResolver contentResolver = getContentResolver();
        // Create the projection, i.e. the values we want to query from the database
        String[] projection = {ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        // The URL for the contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        // Create a cursor, i.e. a pointer to a row in the database
        Cursor cursor = contentResolver.query(uri, projection, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");
        // Create the adapter that maps data from the cursor onto the view

        return cursor;
    }
}
