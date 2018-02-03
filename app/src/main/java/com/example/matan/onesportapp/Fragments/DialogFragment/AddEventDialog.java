package com.example.matan.onesportapp.Fragments.DialogFragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.matan.onesportapp.R;
import com.example.matan.onesportapp.RegisterWindow;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEventDialog extends BaseDialogFragment<AddEventDialog.OnDialogFragmentClickListener> {

    private EditText evDate;
    private EditText evTime;

    public AddEventDialog() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event_dialog, container, false);

        // Init the eventDate and eventTime
        this.evDate = view.findViewById(R.id.evDate);
        this.evTime = view.findViewById(R.id.evTime);
        initDateAndTime();

        // init the dialogs.
        initDataPickerDialog();
        initTimePickerDialog();

        return view;
    }

    private void initDataPickerDialog(){
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelDate(myCalendar);
            }

        };

        this.evDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(),date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initTimePickerDialog(){
        final Calendar mcurrentTime = Calendar.getInstance();

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                mcurrentTime.set(Calendar.HOUR_OF_DAY,selectedHour);
                mcurrentTime.set(Calendar.MINUTE,selectedMinute);
                updateLabelTime(mcurrentTime);
            }
        };

        this.evTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), time, mcurrentTime.get(Calendar.HOUR_OF_DAY), mcurrentTime.get(Calendar.MINUTE), true ).show();
            }
        });
    }

    private void updateLabelDate(Calendar myCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        this.evDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabelTime(Calendar myCalendar) {
        evTime.setText(myCalendar.get(Calendar.HOUR_OF_DAY) + ":" +  myCalendar.get(Calendar.MINUTE));
    }


    private void initDateAndTime(){
        final Calendar myCalendar = Calendar.getInstance();
        updateLabelDate(myCalendar);
        updateLabelTime(myCalendar);
    }

    // interface to handle the dialog click back to the Activity
    public interface OnDialogFragmentClickListener {
        public void onOkClicked(AddEventDialog dialog);
    }
}
