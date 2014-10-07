package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

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
