package se.chalmers.katla.katlaTextToSpeech;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Joel on 09/10/2014.
 */
public interface IKatlaTextToSpeech {
    /**
     * Gets the package name of the default speech synthesis engine.
     * @return the name of the default speech synthesis engine.
     */
    public String getDefaultEngine();

    /**
     * Gets the currently set default language as a locale.
     * @return the Locale of the currently set default language.
     */
    public Locale getDefaultLanguage();

    /**
     * Gets the features(such as engine specific things or framework specified things) supported in
     * the current Locale
     * @param locale the Locale to query for.
     * @return the Set<String> with the features in the specified Locale.
     */
    public Set<String> getFeature(Locale locale);

    /**
     * Gets the currently selected language used by the speech synthesis.
     * @return the Locale with the currently selected language.
     */
    public Locale getLanguage();

    /**
     * Checks if the specified Language(Locale) is available to use.
     * @param locale the Locale to query for.
     * @return codes indicating support status for the locale.
     */
    public int isLanguageAvailable(Locale locale);

    /**
     * Checks if the text to speech service is busy speaking.
     * @return <i>true</i> if it is speaking, <i>false</i> if it is not.
     */
    public boolean isSpeaking();

    /**
     * Speaks the string using the specified queueing mode and speech parameters. The process
     * is asynchronous i.e. it adds the request to it's queue.
     * @param text the String which contains the text to speak
     * @param queueMode the queueing strategy i.e. QUEUE_ADD, QUEUE_FLUSH
     * @param params parameters for the request i.e. KEY_PARAM_STREAM, KEY_PARAM_UTTERANCE_ID,
     *               KEY_PARAM_VOLUME, KEY_PARAM_PAN.
     * @return <i>-1</i> if the speak is not successful, <i>0</i> if the speak is successful
     */
    public int speak(String text, int queueMode, HashMap<String,String> params);

    /**
     * Stops the current utterance and cancels the queue.
     * @return <i>-1</i> if the operation is not successful, <i>0</i> if the operation is successful
     */
    public int stop();

    /**
     * Shuts down the text to speech serivce so it can release all it's resources. It is good
     * practice to do this on onDestroy().
     */
    public void shutdown();

    /**
     * Sets the pitch of the synthesiser.
     * @param pitch speech pitch, 1.0 is the normal value.
     * @return <i>-1</i> if the operation is not successful, <i>0</i> if the operation is successful
     */
    public int setPitch(float pitch);

    /**
     * Plays silence for the specified duration of time using the specified queue mode with the
     * specified parameters.
     * @param durationInMs the amount of time to play silence.
     * @param queueMode the queueing strategy i.e. QUEUE_ADD, QUEUE_FLUSH.
     * @param params parameters for the request i.e. KEY_PARAM_STREAM, KEY_PARAM_UTTERANCE_ID,
     *               KEY_PARAM_VOLUME, KEY_PARAM_PAN.
     * @return <i>-1</i> if the operation is not successful, <i>0</i> if the operation is successful
     */
    public int playSilence(long durationInMs, int queueMode, HashMap<String,String> params);

    /**
     * Sets the text-to-speech language to the specified language.
     * @param locale the language to set.
     * @return parameter describing the supoort status for the locale.
     */
    public int setLanguage(Locale locale);

    /**
     * Gets if the text to speech synthesis is ready to use.
     * @return <i>false</i> if it is not ready, <i>true</i> if it is ready.
     */
    public boolean readyToUse();
}
