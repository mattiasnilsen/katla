package se.chalmers.katla.katlaSpeechToText;

import android.content.ComponentName;
import android.content.Context;

/**
 * Created by Joel on 08/10/2014.
 */
public class KatlaSpeechToTextFactory {
    public static AbstractKatlaSpeechToText createKatlaSpeechToText(Context context) {
        return new KatlaSpeechToText(context);
    }

    public static AbstractKatlaSpeechToText createKatlaSpeechToText(Context context,
                                                             ComponentName serviceComponent) {
        return new KatlaSpeechToText(context, serviceComponent);
    }
}
