package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import se.chalmers.katla.R;
import se.chalmers.katla.katlaSpeechToText.IKatlaSpeechToText;
import se.chalmers.katla.katlaSpeechToText.KatlaRecognitionListener;
import se.chalmers.katla.katlaSpeechToText.KatlaSpeechToTextParameters;
import se.chalmers.katla.katlaSpeechToText.KatlaSpeechToTextFactory;
import se.chalmers.katla.model.IKatla;
import se.chalmers.katla.model.Katla;

public class SpeechToText extends Activity {

    private IKatla katlaInstance;
    private IKatlaSpeechToText kstt;

    private TextView mainTextView;
    private TextView secondaryTextView;
    private boolean isListening;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        katlaInstance = Katla.getInstance();
        kstt = KatlaSpeechToTextFactory.createKatlaSpeechToText(getApplicationContext());

        kstt.setListener(krl);

        isListening = false;

        ImageButton speechButton = (ImageButton)findViewById(R.id.speechToTextButton);
        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpeechToTextButton();
            }
        });
        ImageButton sendButton = (ImageButton)findViewById(R.id.speechToTextSendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ImageButton removeButton = (ImageButton)findViewById(R.id.speechToTextRemoveButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ImageButton changeInputButton = (ImageButton)findViewById(R.id.speechToTextChangeInputButton);
        changeInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mainTextView = (TextView)findViewById(R.id.speechToTextMainText);
        secondaryTextView = (TextView)findViewById(R.id.speechToTextSecondText);
        mainTextView.setText("");
        secondaryTextView.setText("To start speaking: press button!");

    }

    private void onSpeechToTextButton() {
        if(isListening) {
            kstt.stopListening();
        } else {
            Intent recognizerIntent = new Intent();
            recognizerIntent.addCategory(KatlaSpeechToTextParameters.EXTRA_PARTIAL_RESULTS);
            recognizerIntent.addCategory(KatlaSpeechToTextParameters.EXTRA_PROMPT);

            kstt.startListening(recognizerIntent);
            isListening = true;
        }
    }

    @Override
    protected void onDestroy() {
        kstt.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        katlaInstance.setMessage(mainTextView.getText().toString());
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.speech_to_text, menu);
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

    private KatlaRecognitionListener krl = new KatlaRecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {
            isListening = false;
            secondaryTextView.setText("To start speaking: press button!");
        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle bundle) {

        }

        @Override
        public void onPartialResults(Bundle bundle) {
            List<String> resultsList = bundle.getStringArrayList(KatlaSpeechToTextParameters.RESULTS_RECOGNITION);
            secondaryTextView.setText(resultsList.get(0));
            mainTextView.append(resultsList.get(0));
        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }

    };


}
