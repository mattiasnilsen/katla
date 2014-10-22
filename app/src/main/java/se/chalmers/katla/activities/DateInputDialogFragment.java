package se.chalmers.katla.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import se.chalmers.katla.R;

/**
 * DateInputDialogFragment is a dialog that contains a DatePicker where the user can select a time.
 * An activity that instantiates this dialog must implement its interface DateInputDialogListener.
 */
public class DateInputDialogFragment extends DialogFragment {

    private InputDialogListener listener = null;
    private DatePicker datePicker = null;

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

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public Dialog onCreateDialog(Bundle savedInstance) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_date_input, null));

        Dialog dialog = builder.create();
        //The show() method must be called here because of a side effect with dialog.findViewById()
        //For more information see http://stackoverflow.com/q/7568479
        dialog.show();

        datePicker = (DatePicker)dialog.findViewById(R.id.dateDialogDatePicker);
        if(datePicker != null) {
            datePicker.setCalendarViewShown(false);

        }

        final Button doneButton = (Button)dialog.findViewById(R.id.dateDialogDoneButton);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                listener.receiveInput(year + "-" + month + "-" + dayOfMonth);
                dismiss();
            }
        });

        return dialog;
    }

}
