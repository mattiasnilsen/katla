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

    /**
     * {@inheritDoc}
     */
    @Override
    public void cancel() {
        sr.cancel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {
        sr.destroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setListener(RecognitionListener rl) {
        sr.setRecognitionListener(rl);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startListening(Intent recognizerIntent) {
        sr.startListening(recognizerIntent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopListening() {
        sr.stopListening();
    }
}
