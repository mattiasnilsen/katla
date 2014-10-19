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
 * TimeInputDialogFragment is a dialog that contains a TimePicker where the user can select a time.
 * An activity that instantiates this dialog must implement its interface TimeInputDialogListener.
 */
public class TimeInputDialogFragment extends DialogFragment {

    private static final int TIME_PICKER_INTERVAL = 5;
    private InputDialogListener listener = null;
    private TimePicker timePicker;

    /**
     * Override the onAttach method in order to instantiate the listener.
     * @param activity The activity that created this dialog.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (InputDialogListener)activity;
        } catch(ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement InputDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        TimePickerDialog.Builder builder = new TimePickerDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_time_input, null));

        Dialog dialog = builder.create();
        //The show() method must be called here because of a side effect with dialog.findViewById()
        //For more information see http://stackoverflow.com/q/7568479
        dialog.show();

        timePicker = (TimePicker)dialog.findViewById(R.id.timeDialogTimePicker);
        if(timePicker != null) {
            timePicker.setIs24HourView(true);
            timePicker.setCurrentHour(0);
            timePicker.setCurrentMinute(0);
            setInterval(timePicker, TIME_PICKER_INTERVAL);
        }


        final Button doneButton = (Button)dialog.findViewById(R.id.timeDialogDoneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hours = timePicker.getCurrentHour();
                int minutes = timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL;
                if(hours == 0) {
                    listener.receiveInput("" + minutes);
                } else {
                    listener.receiveInput("" + hours + "h " + minutes);
                }
                dismiss();
            }
        });

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
