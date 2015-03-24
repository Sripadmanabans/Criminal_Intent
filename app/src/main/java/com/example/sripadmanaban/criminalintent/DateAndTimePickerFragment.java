package com.example.sripadmanaban.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Date;

/**
 * Used to show an option of which to change
 * Created by Sripadmanaban on 3/24/2015.
 */
public class DateAndTimePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE_TIME =
            "com.example.sripadmanaban.criminalintent.dateTime";

    public static DateAndTimePickerFragment newInstance(Date date) {
        DateAndTimePickerFragment fragment = new DateAndTimePickerFragment();

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE_TIME, date);

        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.date_time_picker_title)
                .setItems(R.array.choice_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 :
                                DatePickerFragment datePicker = DatePickerFragment
                                        .newInstance(getArguments());
                                if(getTargetFragment() != null) {
                                    datePicker.setTargetFragment(getTargetFragment(), 1);
                                    datePicker
                                            .show(getActivity().getSupportFragmentManager(),
                                                    "Date");
                                }
                                break;
                            case 1 :
                                TimePickerFragment timePicker = TimePickerFragment
                                        .newInstance(getArguments());
                                if(getTargetFragment() != null) {
                                    timePicker.setTargetFragment(getTargetFragment(), 2);
                                    timePicker
                                            .show(getActivity().getSupportFragmentManager(),
                                                    "Time");
                                }
                                break;
                        }
                    }
                });

        return builder.create();
    }
}
