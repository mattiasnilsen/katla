package se.chalmers.katla.katlaTextToSpeech;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Joel on 09/10/2014.
 */
public interface IKatlaTextToSpeech {
    public String getDefaultEngine();
    public Locale getDefaultLanguage();
    public Set<String> getFeature(Locale locale);
    public Locale getLanguage();
    public int isLanguageAvailable(Locale locale);
    public boolean isSpeaking();
    public int speak(String text, int queueMode, HashMap<String,String> params);
    public int stop();
    public void shutdown();
    public int setPitch(float pitch);
    public int playEarcon(String earcon, int queueMode, HashMap<String,String> params);
    public int playSilence(long durationInMs, int queueMode, HashMap<String,String> params);
    public boolean readyToUse();
}
