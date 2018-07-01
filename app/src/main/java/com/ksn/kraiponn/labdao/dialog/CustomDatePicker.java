package com.ksn.kraiponn.labdao.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class CustomDatePicker extends DialogFragment {

    public interface onFinishDialogListener{
        void onFinishDialog(String[] dateRes);
    }
    private onFinishDialogListener mCallBack;

    public   CustomDatePicker() {
      //
    }

    public static CustomDatePicker newInstance() {
        CustomDatePicker datePicker = new CustomDatePicker();
        Bundle args = new Bundle();
        datePicker.setArguments(args);
        return datePicker;
    }

    public void setOnItemSelectListener(onFinishDialogListener listener){
        mCallBack = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view,
                                  int year, int month, int dayOfMonth) {
                String[] dateRes = new String[3];
                dateRes[2] = year + "";
                dateRes[1] = (month + 1) + "";
                dateRes[0] = dayOfMonth + "";
                mCallBack.onFinishDialog(dateRes);
            }
        }, yy, mm, dd);


        return dialog;
    }



}
