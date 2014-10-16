package se.chalmers.katla.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.katla.R;

/**
 * Created by kerp on 2014-10-15.
 */
public class TimeInputDialogFragment extends DialogFragment {

    private static final int TIME_PICKER_INTERVAL = 5;
    private TimeInputDialogListener listener = null;

    /**
     * The Activity that instantiates this dialog must implement this interface.
     * The method will be called when the user has chosen a time and presses the done button.
     */
    public interface TimeInputDialogListener {
        public void onTimeSetClicked(int hours, int minutes);
    }

    /**
     * Override the onAttach method in order to instantiate the listener.
     * @param activity The activity that created this dialog.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (TimeInputDialogListener)activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement TimeInputDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        TimePickerDialog.Builder builder = new TimePickerDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_time_input, null));

        Dialog dialog = builder.create();
        dialog.show();

        TimePicker timePicker = (TimePicker)dialog.findViewById(R.id.timeDialogTimePicker);
        if(timePicker != null) {
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(0);
            timePicker.setCurrentMinute(0);
            setInterval(timePicker, TIME_PICKER_INTERVAL);
        }


        return dialog;
    }

    /**
     * Sets the available values to chose from on the minute picker in a TimePicker
     * @param timePicker The TimePicker to change
     * @param interval The interval to set
     */
    private void setInterval(TimePicker timePicker, int interval) {
        Class<?> classForId = null;
        try {
            classForId = Class.forName("com.android.internal.R$id");
            Field field = classForId.getField("minute");
            NumberPicker minutePicker = (NumberPicker)timePicker.findViewById(field.getInt(null));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(60 / interval);
            List<String> displayedValues = new ArrayList<String>();
            for(int i = 0; i < 60; i += interval) {
                displayedValues.add(String.format("%02d", i));
            }
            for(int i = 0; i < 60; i += interval) {
                displayedValues.add(String.format("%02d", i));
            }

            minutePicker.setDisplayedValues(displayedValues.toArray(new String[0]));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
