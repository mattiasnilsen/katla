package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView contactTextView;
    private boolean isListening;
    private String lastResultString;
    private TextView countTextView;

    private final int MAX_SMS_LENGTH = 160;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        katlaInstance = Katla.getInstance();
        kstt = KatlaSpeechToTextFactory.createKatlaSpeechToText(getApplicationContext());

        kstt.setListener(krl);

        isListening = false;

        findViewById(R.id.speechToTextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpeechToTextButton();
            }
        });

        findViewById(R.id.speechToTextSendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        findViewById(R.id.speechToTextRemoveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRemoveButton();
            }
        });

        findViewById(R.id.speechToTextChangeInputButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onToCompositeButton();
            }
        });

        mainTextView = (TextView)findViewById(R.id.speechToTextMainText);
        secondaryTextView = (TextView)findViewById(R.id.speechToTextSecondText);
        contactTextView = (TextView)findViewById(R.id.speechToTextContactField);
        countTextView = (TextView)findViewById(R.id.speechToTextCountField);
        mainTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                countTextView.setText(((charSequence.length()/MAX_SMS_LENGTH + 1) - 
                        charSequence.length()) + "/" + (charSequence.length()/MAX_SMS_LENGTH + 1));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mainTextView.setText(katlaInstance.getMessage());
        secondaryTextView.setText("");
        Toast.makeText(getApplicationContext(), "To start speaking: Press button", Toast.LENGTH_LONG);
        contactTextView.setText(katlaInstance.getPhone());


    }

    private void onSpeechToTextButton() {
        kstt.setListener(krl);
        if(isListening) {
            isListening = false;
            kstt.stopListening();
        } else {
            startListener();
            isListening = true;
        }
    }

    private void startListener() {
        Intent recognizerIntent = new Intent();
        recognizerIntent.putExtra(KatlaSpeechToTextParameters.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(KatlaSpeechToTextParameters.EXTRA_PROMPT, "Speak now");

        kstt.startListening(recognizerIntent);
    }

    private void onRemoveButton() {
        String text = mainTextView.getText().toString();
        text = text.trim();
        mainTextView.setText(removeLastWord(text.length(), text));
    }

    private String removeLastWord(int i, String text) {
        if (text.length() == 0 || text.substring(0, i).length() == 0)
            return "";

        char c = text.charAt(i-1);
        if (c == ' ') {
            return text.substring(0, i);
        } else if (Character.isDigit(c) || Character.isLetter(c)){
            return removeLastWord(i - 1, text);
        } else {
            return text.substring(0, i - 1);
        }


    }

    private void sendMessage() {
        isListening = false;
        kstt.stopListening();
        katlaInstance.setMessage(mainTextView.getText().toString());
        // HUR HANTERA NÄR INTE KONTAKT VALD HÄR? Öppna kontakthanterare och mota input till model?
        // och senskicka och sen byta till nån konversationsvy?
        mainTextView.setText("");
        secondaryTextView.setText("");
        katlaInstance.sendMessage();
    }

    private void onToCompositeButton() {

        // Gå till composite
    }

    @Override
    protected void onDestroy() {
        kstt.destroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        katlaInstance.setMessage(mainTextView.getText().toString());
        isListening = false;
        kstt.stopListening();
        kstt.cancel();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mainTextView.setText(katlaInstance.getMessage());
        super.onResume();
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
            secondaryTextView.setText("");
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
            if (isListening) {
                startListener();
            } else {
                secondaryTextView.setText("");
                Toast.makeText(getApplicationContext(), "Stopped recognition", Toast.LENGTH_LONG);
            }
        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle bundle) {

        }

        @Override
        public void onPartialResults(Bundle bundle) {
            secondaryTextView.setText("");
            List<String> resultsList = bundle.getStringArrayList(KatlaSpeechToTextParameters.RESULTS_RECOGNITION);
            if (!resultsList.get(0).equals(lastResultString)) {
                lastResultString = resultsList.get(0);
                String[] text = resultsList.get(0).split(" ");
                secondaryTextView.setText(text[text.length - 1]);
                mainTextView.append(text[text.length - 1] + " ");
            }

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }

    };


}
