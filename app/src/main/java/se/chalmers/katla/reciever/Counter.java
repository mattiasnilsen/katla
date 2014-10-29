package se.chalmers.katla.reciever;

/**
 * Created by Lisa on 2014-10-28.
 */
public class Counter {
    private static int numberOfNotifications = 0;

    public static void increment(){
        numberOfNotifications++;
    }
    public static void reset(){
        numberOfNotifications=0;
    }
    public static int getNumberOfNotifications(){
        return numberOfNotifications;
    }
}
