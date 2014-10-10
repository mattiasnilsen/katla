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
    /**
     * {@inheritDoc}
     */
    @Override
    public String getDefaultEngine() {
        return ttsInstance.getDefaultEngine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale getDefaultLanguage() {
        return ttsInstance.getDefaultLanguage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getFeature(Locale locale) {
        return ttsInstance.getFeatures(locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Locale getLanguage() {
        return ttsInstance.getLanguage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int isLanguageAvailable(Locale locale) {
        return ttsInstance.isLanguageAvailable(locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSpeaking() {
        return ttsInstance.isSpeaking();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int speak(String text, int queueMode, HashMap<String, String> params) {
        return ttsInstance.speak(text, queueMode, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int stop() {
        return ttsInstance.stop();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdown() {
        ttsInstance.shutdown();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int setPitch(float pitch) {
        return ttsInstance.setPitch(pitch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int playSilence(long durationInMs, int queueMode, HashMap<String, String> params) {
        return ttsInstance.playSilence(durationInMs, queueMode, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean readyToUse() {
        return isReadyToUse;
    }
}
