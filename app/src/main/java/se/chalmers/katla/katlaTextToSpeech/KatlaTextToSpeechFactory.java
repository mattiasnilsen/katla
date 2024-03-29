package se.chalmers.katla.katlaTextToSpeech;

import android.content.Context;

/**
 * Created by Joel on 09/10/2014.
 */
public class KatlaTextToSpeechFactory {
    public static IKatlaTextToSpeech createKatlaTextToSpeech(Context context) {
        return new KatlaTextToSpeech(context);
    }

    public static IKatlaTextToSpeech createKatlaTextToSpeech(Context context, String engine) {
        return new KatlaTextToSpeech(context, engine);
    }
}
