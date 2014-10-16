package se.chalmers.katla.activities;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.katla.R;

/**
 * Created by kerp on 2014-10-15.
 */
public class TimeInputDialogFragment extends DialogFragment implements TimePicker.OnTimeChangedListener {

    private boolean mIgnoreEvent = false;
    private static final int TIME_PICKER_INTERVAL = 5;

    public Dialog onCreateDialog(Bundle savedInstance) {
        TimePickerDialog.Builder builder = new TimePickerDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();



        builder.setView(inflater.inflate(R.layout.fragment_time_input, null));
        Dialog dialog = builder.create();
        dialog.show();
        TimePicker timePicker = (TimePicker)dialog.findViewById(R.id.timePicker);
        if(timePicker != null) {
            timePicker.setIs24HourView(true);
            setInterval(timePicker);
            //timePicker.setOnTimeChangedListener(this);
        }


        return dialog;
    }

    private void setInterval(TimePicker timePicker) {
        Class<?> classForId = null;
        try {
            classForId = Class.forName("com.android.internal.R$id");
            Field field = classForId.getField("minute");
            NumberPicker minutePicker = (NumberPicker)timePicker.findViewById(field.getInt(null));
            minutePicker.setMinValue(0);
            minutePicker.setMaxValue(60 / TIME_PICKER_INTERVAL);
            List<String> displayedValues = new ArrayList<String>();
            for(int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            for(int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
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

    @Override
    public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
        System.out.println("BLALABLABLABLABLA LOOK AT ME");
        if (mIgnoreEvent)
            return;
        if (minute % TIME_PICKER_INTERVAL != 0) {
            int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
            minute = minuteFloor + (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
            if (minute == 60)
                minute = 0;
            mIgnoreEvent = true;
            timePicker.setCurrentMinute(minute);
            mIgnoreEvent = false;
        }
    }
}
