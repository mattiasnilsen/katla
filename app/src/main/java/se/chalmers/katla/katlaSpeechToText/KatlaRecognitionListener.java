package se.chalmers.katla.katlaSpeechToText;

import android.os.Bundle;
import android.speech.RecognitionListener;

/**
 * Created by Joel on 13/10/2014.
 */
public interface KatlaRecognitionListener{

    public void onReadyForSpeech(Bundle bundle);
    public void onBeginningOfSpeech();
    public void onRmsChanged(float v);
    public void onBufferReceived(byte[] bytes);
    public void onEndOfSpeech();
    public void onError(int i);
    public void onResults(Bundle bundle);
    public void onPartialResults(Bundle bundle);
    public void onEvent(int i, Bundle bundle);


}
