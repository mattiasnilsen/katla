package se.chalmers.katla.katlaSpeechToText;

import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

/**
 * Created by Joel on 08/10/2014.
 */
public abstract class AbstractKatlaSpeechToText {
    public abstract void cancel();
    public abstract void destroy();
    public static boolean isRecognitionAvailable(Context context) {
        return SpeechRecognizer.isRecognitionAvailable(context);
    }
    public abstract void setListener(RecognitionListener rl);
    public abstract void startListening(Intent recognizerIntent);
    public abstract void stopListening();
}
