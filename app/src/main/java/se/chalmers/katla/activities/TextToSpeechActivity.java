package se.chalmers.katla.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import se.chalmers.katla.R;
import se.chalmers.katla.katlaTextToSpeech.IKatlaTextToSpeech;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeech;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechFactory;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechParameters;

public class TextToSpeechActivity extends Activity {

    private Button speakButton;
    private TextView speakView;
    private IKatlaTextToSpeech ktts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        ktts = KatlaTextToSpeechFactory.createKatlaTextToSpeech(getApplicationContext());

        speakButton = (Button)findViewById(R.id.speakButton);
        speakView = (TextView)findViewById(R.id.speakTextField);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpeakPress();
            }
        });
    }

    /**
     * Handles what happens when you want text to be spoken.
     */
    private void onSpeakPress() {
        if(ktts.readyToUse()) {
            // QUEUE_ADD adds this speak to a queue, QUEUE_FLUSH removes everything from the queue
            // and speaks your message.
            ktts.speak(speakView.getText().toString(), KatlaTextToSpeechParameters.QUEUE_FLUSH, null);
        } else {
            Toast.makeText(getApplicationContext(), "Text to speech unavailable at this time",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.text_to_speech, menu);
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
    protected void onPause() {
        // stop() clears the queue and stops speaking.
        ktts.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ktts.shutdown();
        super.onDestroy();
    }
}
