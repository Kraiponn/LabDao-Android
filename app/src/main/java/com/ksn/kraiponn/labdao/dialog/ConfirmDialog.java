package com.ksn.kraiponn.labdao.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class ConfirmDialog extends DialogFragment {
    public enum BUTTONS {
        NEGATIVE, POSITIVE
    }

    public interface onFinishDialogListener{
        void onFinishDialog(ConfirmDialog.BUTTONS button);
    }

    private onFinishDialogListener mCallBack;

    public static ConfirmDialog newInstance(
            String msg, String textNeg,
            String textPos
    ) {
        ConfirmDialog fm = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString("msg", msg);
        args.putString("textNeg", textNeg);
        args.putString("textPos", textPos);
        fm.setArguments(args);
        return fm;
    }


    public void setOnFinishDialogListener(onFinishDialogListener listener) {
        mCallBack = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String msg = getArguments().getString("msg");
        String textNeg = getArguments().getString("textNeg");
        String textPos = getArguments().getString("textPos");

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                .setMessage(msg)
                .setNegativeButton(textNeg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallBack.onFinishDialog(BUTTONS.NEGATIVE);
                    }
                })
                .setPositiveButton(textPos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallBack.onFinishDialog(BUTTONS.POSITIVE);
                    }
                });

        return dialog.create();
    }


}
