package se.chalmers.katla.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import se.chalmers.katla.R;
import se.chalmers.katla.eventBus.EventBus;
import se.chalmers.katla.views.ExampleView;

/**
 * Created by Kalior on 28/09/2014.
 */
public abstract class ExampleActivity extends Activity {
    // private Model model
    // Above is an instance to the model however we want to do that. Perhaps each activity has
    // a reference to the part of the model that is relevant to it.
    private ExampleView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //model = get instance of model
        view = (ExampleView) View.inflate(this, R.layout.example_view, null);
        view.setViewListener(viewListener);
        this.setContentView(view);
        EventBus.getInstance().registerListener(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().removeListener(view);
    }

    /**
     * Implementation for the ViewListener interface in your view(the view that belongs to this
     * controller)
     */
    private ExampleView.ViewListener viewListener = new ExampleView.ViewListener() {
        // implement methods that are specified in ViewListener for your view class and
        // make them call methods here.
    };

}
