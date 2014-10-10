package se.chalmers.katla.katlaSpeechToText;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

/**
 * Created by Joel on 08/10/2014.
 */
public abstract class AbstractKatlaSpeechToText {
    /**
     * Cancels the the speech to text service.
     */
    public abstract void cancel();

    /**
     * Destroys the speech to text service.
     */
    public abstract void destroy();

    /**
     * Checks whether the speech to text service is available.
     * @param context the context the app runs in.
     * @return <i>false</i> if the service is not avilable, <i>true</i> if it is available.
     */
    public static boolean isRecognitionAvailable(Context context) {
        return SpeechRecognizer.isRecognitionAvailable(context);
    }

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
