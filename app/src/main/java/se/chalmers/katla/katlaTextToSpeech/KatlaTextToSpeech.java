package se.chalmers.katla.katlaTextToSpeech;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Must be initialized before use is allowed. Always check readyToUse() before using.
 * Created by Joel on 09/10/2014.
 */
public class KatlaTextToSpeech implements TextToSpeech.OnInitListener, IKatlaTextToSpeech{

    private boolean isReadyToUse;
    private TextToSpeech ttsInstance;

    public KatlaTextToSpeech(Context context) {
        ttsInstance = new TextToSpeech(context, this);
        isReadyToUse = false;
    }

    public KatlaTextToSpeech(Context context, String engine) {
        ttsInstance = new TextToSpeech(context, this, engine);
        isReadyToUse = false;
    }

    @Override
    public void onInit(int i) {
        isReadyToUse = true;
    }

    @Override
    public String getDefaultEngine() {
        return ttsInstance.getDefaultEngine();
    }

    @Override
    public Locale getDefaultLanguage() {
        return ttsInstance.getDefaultLanguage();
    }

    @Override
    public Set<String> getFeature(Locale locale) {
        return ttsInstance.getFeatures(locale);
    }

    @Override
    public Locale getLanguage() {
        return ttsInstance.getLanguage();
    }

    @Override
    public int isLanguageAvailable(Locale locale) {
        return ttsInstance.isLanguageAvailable(locale);
    }

    @Override
    public boolean isSpeaking() {
        return ttsInstance.isSpeaking();
    }

    @Override
    public int speak(String text, int queueMode, HashMap<String, String> params) {
        return ttsInstance.speak(text, queueMode, params);
    }

    @Override
    public int stop() {
        return ttsInstance.stop();
    }

    @Override
    public void shutdown() {
        ttsInstance.shutdown();
    }

    @Override
    public int setPitch(float pitch) {
        return ttsInstance.setPitch(pitch);
    }

    @Override
    public int playEarcon(String earcon, int queueMode, HashMap<String, String> params) {
        return ttsInstance.playEarcon(earcon, queueMode, params);
    }

    @Override
    public int playSilence(long durationInMs, int queueMode, HashMap<String, String> params) {
        return ttsInstance.playSilence(durationInMs, queueMode, params);
    }

    @Override
    public boolean readyToUse() {
        return isReadyToUse;
    }
}
