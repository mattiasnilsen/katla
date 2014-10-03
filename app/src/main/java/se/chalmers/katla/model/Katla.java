package se.chalmers.katla.model;


/**
 * Created by Anton on 2014-10-02.
 */
public class Katla {

    private static Katla ourInstance;

    public static Katla getInstance() {
        if(ourInstance == null) {
            ourInstance = new Katla();
        }
        return ourInstance;
    }


    private Katla() {

    }

}
