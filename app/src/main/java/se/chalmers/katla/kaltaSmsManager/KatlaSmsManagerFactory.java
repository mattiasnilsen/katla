package se.chalmers.katla.kaltaSmsManager;

/**
 * Created by Joel on 10/10/2014.
 */
public class KatlaSmsManagerFactory {
    public static IKatlaSmsManager createIKatlaSmsManager() {
        return new KatlaSmsManager();
    }
}
