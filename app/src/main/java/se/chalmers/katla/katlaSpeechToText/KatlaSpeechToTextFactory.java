package se.chalmers.katla.katlaSpeechToText;

import android.content.ComponentName;
import android.content.Context;

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
    public static AbstractKatlaSpeechToText createKatlaSpeechToText(Context context) {
        return new KatlaSpeechToText(context);
    }

    /**
     *
     * @param context the context the context the app runs in.
     * @param serviceComponent the componentName of a specific service to direct this
     *                         speech to text to.
     * @return a new AbstractKatlaSpeechToText
     */
    public static AbstractKatlaSpeechToText createKatlaSpeechToText(Context context,
                                                             ComponentName serviceComponent) {
        return new KatlaSpeechToText(context, serviceComponent);
    }
}
