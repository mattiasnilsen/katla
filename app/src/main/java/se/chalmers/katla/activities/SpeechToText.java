package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import se.chalmers.katla.R;
import se.chalmers.katla.eventBus.EventListener;
import se.chalmers.katla.katlaSpeechToText.IKatlaSpeechToText;
import se.chalmers.katla.katlaSpeechToText.KatlaRecognitionListener;
import se.chalmers.katla.katlaSpeechToText.KatlaSpeechToTextParameters;
import se.chalmers.katla.katlaSpeechToText.KatlaSpeechToTextFactory;
import se.chalmers.katla.katlaTextToSpeech.IKatlaTextToSpeech;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechFactory;
import se.chalmers.katla.katlaTextToSpeech.KatlaTextToSpeechParameters;
import se.chalmers.katla.model.IKatla;
import se.chalmers.katla.model.Katla;

public class SpeechToText extends Activity implements EventListener{

    private IKatla katlaInstance;
    private IKatlaSpeechToText kstt;
    private IKatlaTextToSpeech ktts;

    private EditText mainTextView;
    private TextView contactTextView;
    private TextView phoneTextView;
    private boolean isListeningToSpeech;
    private String lastResultString;
    private TextView countTextView;
    private ImageButton speechToTextBtn;

    private final int MAX_SMS_LENGTH = 160;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_to_text);

        katlaInstance = Katla.getInstance();
        if (KatlaSpeechToTextFactory.isRecognitionAvailable(getApplicationContext())) {
            kstt = KatlaSpeechToTextFactory.createKatlaSpeechToText(getApplicationContext());
            kstt.setListener(krl);
        }

        ktts = KatlaTextToSpeechFactory.createKatlaTextToSpeech(getApplicationContext());

        isListeningToSpeech = false;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        GridLayout btnBar = (GridLayout) findViewById(R.id.buttonBarSTT);
        ViewGroup.LayoutParams params = btnBar.getLayoutParams();
        params.width = size.x;
        params.height = size.x / 3;


        LinearLayout contactLayout = (LinearLayout) findViewById(R.id.contactLayoutSTT);
        ImageButton callBtn = (ImageButton) findViewById(R.id.callBtnSTT);
        speechToTextBtn = (ImageButton) findViewById(R.id.speechToTextButton);
        ImageButton removeBtn = (ImageButton) findViewById(R.id.removeBtnSTT);
        ImageButton sendBtn = (ImageButton) findViewById(R.id.sendBtnSTT);
        ImageButton composeBtn = (ImageButton) findViewById(R.id.composeBtnSTT);
        params = removeBtn.getLayoutParams();
        params.width = size.x / 3;
        params.height = size.x / 3;
        params = sendBtn.getLayoutParams();
        params.width = size.x / 3;
        params.height = size.x / 3;
        params = composeBtn.getLayoutParams();
        params.width = size.x / 3;
        params.height = size.x / 3;
        params = speechToTextBtn.getLayoutParams();
        params.width = size.x / 6;
        params.height = size.x / 6;
        params = callBtn.getLayoutParams();
        params.width = size.x / 6;
        params.height = size.x / 6;


        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactServiceIntent = new Intent(SpeechToText.this, ContactService.class);

                startActivity(contactServiceIntent);
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRemoveButton();
            }
        });

        composeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onToCompositeButton();
            }
        });

        speechToTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSpeechToTextButton();
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                // According to documentation ACTION_CALL can not call emergency numbers?
                intent.setData(Uri.parse("tel:" + katlaInstance.getPhone()));
                startActivity(intent);
            }
        });

        mainTextView = (EditText) findViewById(R.id.speechToTextMainText);
        contactTextView = (TextView) findViewById(R.id.contactSTT);
        phoneTextView = (TextView) findViewById(R.id.phoneSTT);
        countTextView = (TextView) findViewById(R.id.speechToTextCountField);
        mainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTextToSpeechButton();
            }
        });
        mainTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                countTextView.setText(((charSequence.length() / MAX_SMS_LENGTH + 1) * MAX_SMS_LENGTH -
                        charSequence.length()) + "/" + (charSequence.length() / MAX_SMS_LENGTH + 1));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mainTextView.setText(katlaInstance.getMessage());
        contactTextView.setText(katlaInstance.getContact());
        phoneTextView.setText(katlaInstance.getPhone());

        mainTextView.setSelection(mainTextView.getText().length());

        if (katlaInstance.getDistractionLevel() == 0) {
            mainTextView.setFocusable(true);
        } else {
            mainTextView.setFocusable(false);
        }
    }

    private void onSpeechToTextButton() {
        if (KatlaSpeechToTextFactory.isRecognitionAvailable(getApplicationContext())) {
            kstt.setListener(krl);
            if (isListeningToSpeech) {
                isListeningToSpeech = false;
                kstt.stopListening();
            } else {
                speechToTextBtn.setBackgroundColor(getResources().getColor(R.color.BlueDark));
                startListener();
                isListeningToSpeech = true;
                Toast.makeText(getApplicationContext(), "Start speaking now", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Speech to text is unavailable at this time",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void onTextToSpeechButton() {
        if(ktts.readyToUse()) {
            // QUEUE_ADD adds this speak to a queue, QUEUE_FLUSH removes everything from the queue
            // and speaks your message.
            ktts.speak(mainTextView.getText().toString(), KatlaTextToSpeechParameters.QUEUE_FLUSH, null);
        } else {
            Toast.makeText(getApplicationContext(), "Text to speech unavailable at this time",
                    Toast.LENGTH_SHORT).show();
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
        mainTextView.setSelection(mainTextView.getText().length());
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
        isListeningToSpeech = false;
        if (KatlaSpeechToTextFactory.isRecognitionAvailable(getApplicationContext())) {
            kstt.stopListening();
        }
        katlaInstance.setMessage(mainTextView.getText().toString());
        // HUR HANTERA NÄR INTE KONTAKT VALD HÄR? Öppna kontakthanterare och mota input till model?
        // och senskicka och sen byta till nån konversationsvy?
        mainTextView.setText("");
        katlaInstance.sendMessage();
    }

    private void onToCompositeButton() {
        Intent compositeIntent = new Intent(SpeechToText.this, SwipeMainActivity.class);

        startActivity(compositeIntent);
    }

    @Override
    protected void onDestroy() {
        if (KatlaSpeechToTextFactory.isRecognitionAvailable(getApplicationContext())) {
            kstt.destroy();
        }
        ktts.shutdown();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        katlaInstance.setMessage(mainTextView.getText().toString());
        katlaInstance.setContact(contactTextView.getText()+"");
        katlaInstance.setPhone(phoneTextView.getText()+"");
        if (KatlaSpeechToTextFactory.isRecognitionAvailable(getApplicationContext())) {
            isListeningToSpeech = false;
            kstt.stopListening();
            kstt.cancel();
        }
        ktts.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mainTextView.setText(katlaInstance.getMessage());
        contactTextView.setText(katlaInstance.getContact());
        phoneTextView.setText(katlaInstance.getPhone());


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
            if (isListeningToSpeech) {
                startListener();
            } else {
                speechToTextBtn.setBackgroundColor(getResources().getColor(R.color.BlueLight));
                Toast.makeText(getApplicationContext(), "Stopped recognition", Toast.LENGTH_LONG).show();
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
            List<String> resultsList = bundle.getStringArrayList(KatlaSpeechToTextParameters.RESULTS_RECOGNITION);
            if (!resultsList.get(0).equals(lastResultString)) {
                lastResultString = resultsList.get(0);
                String[] text = resultsList.get(0).split(" ");
                mainTextView.append(text[text.length - 1] + " ");
            }

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }

    };


    @Override
    public void receiveEvent(String s, Object o) {
        if (s == "Driver distraction changed") {
            if ((Integer)o == 0) {
                mainTextView.setFocusable(true);
            } else {
                mainTextView.setFocusable(false);
            }
        }
    }
}
