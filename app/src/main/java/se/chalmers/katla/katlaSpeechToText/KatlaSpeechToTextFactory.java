package se.chalmers.katla.katlaSpeechToText;

import android.content.ComponentName;
import android.content.Context;
import android.speech.SpeechRecognizer;

/**
 * Factory class for receiving a speech to text service.
 * Created by Joel on 08/10/2014.
 */
public class KatlaSpeechToTextFactory {
    /**
     * Factory for receiving a speech to text service.
     * @param context the context the app runs in.
     * @return a new AbstractKatlaSpeechToText.
     */
    public static IKatlaSpeechToText createKatlaSpeechToText(Context context) {
        return new KatlaSpeechToText(context);
    }

    /**
     *
     * @param context the context the context the app runs in.
     * @param serviceComponent the componentName of a specific service to direct this
     *                         speech to text to.
     * @return a new AbstractKatlaSpeechToText
     */
    public static IKatlaSpeechToText createKatlaSpeechToText(Context context,
                                                             ComponentName serviceComponent) {
        return new KatlaSpeechToText(context, serviceComponent);
    }

    /**
     * Checks whether the speech to text service is available.
     * @param context the context the app runs in.
     * @return <i>false</i> if the service is not avilable, <i>true</i> if it is available.
     */
    public static boolean isRecognitionAvailable(Context context) {
        return SpeechRecognizer.isRecognitionAvailable(context);
    }
}
