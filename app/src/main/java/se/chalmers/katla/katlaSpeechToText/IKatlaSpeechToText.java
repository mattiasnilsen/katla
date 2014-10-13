package se.chalmers.katla.katlaSpeechToText;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

/**
 * Created by Joel on 08/10/2014.
 */
public abstract interface IKatlaSpeechToText {
    /**
     * Cancels the the speech to text service.
     */
    public abstract void cancel();

    /**
     * Destroys the speech to text service.
     */
    public abstract void destroy();



    /**
     * Sets a listener on the speech to text service that will receive all callbacks
     * @param rl the listener to set on the serivce
     */
    public abstract void setListener(RecognitionListener rl);

    /**
     * Starts listening for speech.
     * @param recognizerIntent contains parameters for the speech recognition.
     */
    public abstract void startListening(Intent recognizerIntent);

    /**
     * Stops listening for speech.
     */
    public abstract void stopListening();
}
