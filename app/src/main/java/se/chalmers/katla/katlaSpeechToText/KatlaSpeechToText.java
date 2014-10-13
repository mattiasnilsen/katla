package se.chalmers.katla.katlaSpeechToText;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

/**
 * Created by Joel on 08/10/2014.
 */
public class KatlaSpeechToText implements IKatlaSpeechToText {
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
    public void setListener(final KatlaRecognitionListener rl) {
        sr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
                rl.onReadyForSpeech(bundle);
            }

            @Override
            public void onBeginningOfSpeech() {
                rl.onBeginningOfSpeech();
            }

            @Override
            public void onRmsChanged(float v) {
                rl.onRmsChanged(v);
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
                rl.onBufferReceived(bytes);
            }

            @Override
            public void onEndOfSpeech() {
                rl.onEndOfSpeech();
            }

            @Override
            public void onError(int i) {
                rl.onError(i);
            }

            @Override
            public void onResults(Bundle bundle) {
                rl.onResults(bundle);
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                rl.onPartialResults(bundle);
            }

            @Override
            public void onEvent(int i, Bundle bundle) {
                rl.onEvent(i, bundle);
            }
        });
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
