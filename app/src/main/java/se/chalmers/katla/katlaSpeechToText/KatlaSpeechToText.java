package se.chalmers.katla.katlaSpeechToText;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

/**
 * Created by Joel on 08/10/2014.
 */
public class KatlaSpeechToText extends AbstractKatlaSpeechToText {
    private SpeechRecognizer sr;

    public KatlaSpeechToText(Context context) {
        sr = SpeechRecognizer.createSpeechRecognizer(context);
    }

    public KatlaSpeechToText(Context context, ComponentName serviceComponent) {
        sr = SpeechRecognizer.createSpeechRecognizer(context, serviceComponent);
    }
    @Override
    public void cancel() {
        sr.cancel();
    }

    @Override
    public void destroy() {
        sr.destroy();
    }

    @Override
    public void setListener(RecognitionListener rl) {
        sr.setRecognitionListener(rl);
    }

    @Override
    public void startListening(Intent recognizerIntent) {
        sr.startListening(recognizerIntent);
    }

    @Override
    public void stopListening() {
        sr.stopListening();
    }
}
