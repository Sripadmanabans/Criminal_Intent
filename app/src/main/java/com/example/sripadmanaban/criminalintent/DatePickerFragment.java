package com.example.sripadmanaban.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Used to display the date as the picker
 * Created by Sripadmanaban on 3/23/2015.
 */
public class DatePickerFragment extends DialogFragment {

    private Date mDate;

    public static DatePickerFragment newInstance(Bundle args) {

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments()
                .getSerializable(DateAndTimePickerFragment.EXTRA_DATE_TIME);

        // Creating a calendar to get the year, month and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);

        View view = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Translate year, month day into a Date object using a calendar
                mDate = new GregorianCalendar(year,
                        monthOfYear,
                        dayOfMonth,
                        hour,
                        minute,
                        second)
                        .getTime();

                Log.d("DatePickerFragment", mDate.toString());

                // Update arguments to preserve selected value on rotation
                getArguments()
                        .putSerializable(DateAndTimePickerFragment.EXTRA_DATE_TIME, mDate);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.date_picker_title);
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_OK);
            }
        });

        return builder.create();
    }

    private void sendResult(int resultCode) {
        if(getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(DateAndTimePickerFragment.EXTRA_DATE_TIME, mDate);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
