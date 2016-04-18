package com.example.vinay.project239;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Vinay on 12/5/2015.
 */
public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    public DateDialog(){
        //super(DateDialog.class,R.layout.activity_notification,null);
    }

    /*public static final DateDialog newInstance(String title, String message, String buttonText) {
        DateDialog adf =  new DateDialog();
        Bundle bundle = new Bundle(2);
        bundle.putString("title", title);
        bundle.putString("message", message);
       // bundle.putString("buttonText", buttonText);
        adf.setArguments(bundle);
        return adf;
    }*/

    EditText etFrom,etTo;

    public void setView(View view){
        etFrom = (EditText) view;
        etTo = (EditText) view;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        etFrom.setText(date);
        //String edate = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
        //etTo.setText(edate);
    }
}
