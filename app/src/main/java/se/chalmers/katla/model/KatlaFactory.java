package se.chalmers.katla.model;

/**
 * KatlaFactory is used to instantiate the class Katla
 */
public class KatlaFactory {
    public static IKatla createKatla() {
        return Katla.getInstance();
    }
}
