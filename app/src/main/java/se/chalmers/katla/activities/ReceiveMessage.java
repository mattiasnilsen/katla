package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.policy.AutomotiveCertificate;

import se.chalmers.katla.R;
import se.chalmers.katla.katlaTextToSpeech.IKatlaTextToSpeech;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechFactory;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechParameters;
import se.chalmers.katla.model.IKatla;
import se.chalmers.katla.model.Katla;

public class ReceiveMessage extends Activity {
    IKatla model;
    private IKatlaTextToSpeech ktts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);
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
                Intent speechToTextIntent = new Intent(ReceiveMessage.this, ViewMessage.class);

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

        new AsyncTask() {      //Network operation must be run on separate thread than main thread
            @Override
            protected Object doInBackground(Object... objects) {
                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]), //Provided certificate
                        new AutomotiveListener() {
                            @Override
                            public void receive(AutomotiveSignal automotiveSignal) {
                                Log.v("Demo", "Speed is: " + ((SCSFloat) automotiveSignal.getData()).getFloatValue());
                            }

                            @Override
                            public void timeout(int i) {
                            }

                            @Override
                            public void notAllowed(int i) {
                            }
                        },
                        new DriverDistractionListener() {       //Listener for driver distraction level
                            @Override
                            public void levelChanged(DriverDistractionLevel driverDistractionLevel) {
                                Log.v("Demo", "Driver distraction level is: " + driverDistractionLevel.getLevel());
                            }
                        }
                ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);
                return null;
            }
        }.execute();

        AutomotiveListener automotiveListener = new AutomotiveListener() {
            @Override
            public void receive(AutomotiveSignal automotiveSignal) {
                System.out.println(automotiveSignal.getData().toString());
            }

            @Override
            public void timeout(int i) {

            }

            @Override
            public void notAllowed(int i) {

            }
        };

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
}
