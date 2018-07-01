package com.ksn.kraiponn.labdao.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ksn.kraiponn.labdao.R;

public class InputDialog extends DialogFragment {

    public enum INPUT_TYPE {
        STRING, NUMBER, PASSWORD
    }

    public interface onFinishDialogListener{
        void onFinishDialog(String result);
    }

    private onFinishDialogListener mCallBack;

    public static InputDialog newInstance(
            String msg,
            String defaultValue,
            INPUT_TYPE type
    ) {
        InputDialog fm = new InputDialog();
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        args.putString("msg", msg);
        args.putString("defValue", defaultValue);
        fm.setArguments(args);
        return fm;
    }


    public void setOnFinishDialogListener(onFinishDialogListener listener) {
        mCallBack = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        INPUT_TYPE type = (INPUT_TYPE) getArguments().getSerializable("type");
        String msg = getArguments().getString("msg");
        String defValue = getArguments().getString("defValue");

        View view = getActivity().getLayoutInflater().inflate(
                R.layout.custom_input_layout, null
        );

        TextView tvTitleMessage = view.findViewById(R.id.text_message);
        final EditText edtInput = view.findViewById(R.id.edit_input);

        if (type == INPUT_TYPE.STRING) {
            edtInput.setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (type == INPUT_TYPE.NUMBER) {
            edtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (type == INPUT_TYPE.PASSWORD) {
            edtInput.setInputType(
                    InputType.TYPE_TEXT_VARIATION_PASSWORD|
                    InputType.TYPE_CLASS_TEXT);
        }

        tvTitleMessage.setText(msg);
        edtInput.setText(defValue);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallBack.onFinishDialog(edtInput.getText().toString().trim());
                    }
                });

        return dialog.create();
    }


}
