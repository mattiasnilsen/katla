package se.chalmers.katla.agaListenerService;

import android.os.AsyncTask;
import android.swedspot.automotiveapi.AutomotiveSignal;
import android.swedspot.automotiveapi.AutomotiveSignalId;
import android.swedspot.scs.data.SCSFloat;

import com.swedspot.automotiveapi.AutomotiveFactory;
import com.swedspot.automotiveapi.AutomotiveListener;
import com.swedspot.vil.distraction.DriverDistractionLevel;
import com.swedspot.vil.distraction.DriverDistractionListener;
import com.swedspot.vil.policy.AutomotiveCertificate;

import se.chalmers.katla.eventBus.EventBus;

/**
 * Created by Joel on 17/10/2014.
 */
public class AgaListener {

    EventBus eventBusInstance;

    public AgaListener() {

        eventBusInstance = EventBus.getInstance();

        new AsyncTask() {      //Network operation must be run on separate thread than main thread
            @Override
            protected Object doInBackground(Object... objects) {
                AutomotiveFactory.createAutomotiveManagerInstance(
                        new AutomotiveCertificate(new byte[0]), //Provided certificate
                        new AutomotiveListener() {
                            @Override
                            public void receive(AutomotiveSignal automotiveSignal) {
                                onSpeedChanged(((SCSFloat) automotiveSignal.getData()).getFloatValue());
                            }

                            @Override
                            public void timeout(int i) {
                            }

                            @Override
                            public void notAllowed(int i) {
                            }
                        },
                        new DriverDistractionListener() {       //Listener for driver distraction level
                            @Override
                            public void levelChanged(DriverDistractionLevel driverDistractionLevel) {
                                onDistractionChanged(driverDistractionLevel.getLevel());
                            }
                        }
                ).register(AutomotiveSignalId.FMS_WHEEL_BASED_SPEED);
                return null;
            }
        }.execute();
    }

    private void onSpeedChanged(float f) {
        eventBusInstance.sendEvent("Speed changed", f);
    }

    private void onDistractionChanged(int i) {
        int j = -1;
        if (i <= 0) {
            j = 0;
        } else
            j = 1;
        eventBusInstance.sendEvent("Driver distraction changed", j);
    }
}
