package com.ksn.kraiponn.labdao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ksn.kraiponn.labdao.R;
import com.ksn.kraiponn.labdao.activity.ScanCodeActivity;
import com.ksn.kraiponn.labdao.dialog.ConfirmDialog;
import com.ksn.kraiponn.labdao.dialog.InputDialog;

public class MainFragment extends Fragment {

    /***********************
     *  Variable Zone
     */
    private Button btnIncomeExpense;
    private Button btnOkHttp;
    private Button btnRetrofit;
    private Button btnVolley;

    public interface onOkHttpClickListener{
        void onOkHttpClicked();
    }

    public interface onIncomeExpenseClickListener{
        void onIncomeExpenseClick();
    }

    private onOkHttpClickListener mCallBack;
    private onIncomeExpenseClickListener mCallBackIcExp;

    public MainFragment() {

    }

    public static MainFragment newInstance() {
        MainFragment fm = new MainFragment();
        Bundle args = new Bundle();
        fm.setArguments(args);
        return fm;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_main,
                container, false
        );

        initInstance(rootView);
        return rootView;
    }


    /******************************
     *  Method Zone
     *****************************/
    private void initInstance(View rootView) {
        btnIncomeExpense = rootView.findViewById(R.id.button_income_expense);
        btnOkHttp = rootView.findViewById(R.id.button_okhttp);
        btnRetrofit = rootView.findViewById(R.id.button_retrofit);
        btnVolley = rootView.findViewById(R.id.button_volley);

        btnIncomeExpense.setOnClickListener(btnIncomeExpenseClickListener);
        btnOkHttp.setOnClickListener(btnOkHttpClickListener);
    }

    private void showToast(String text) {
        Toast.makeText(getContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }

    private void callInputDialog() {
        FragmentManager fm = getFragmentManager();
        InputDialog dialog = InputDialog.newInstance(
                "Please enter your data",
                "",
                InputDialog.INPUT_TYPE.STRING
        );
        dialog.show(fm, null);
        dialog.setOnFinishDialogListener(new InputDialog.onFinishDialogListener() {
            @Override
            public void onFinishDialog(String result) {
                showToast(result);
            }
        });
    }

    private void callConfirmDialog() {
        FragmentManager fm = getFragmentManager();
        ConfirmDialog dialog = ConfirmDialog.newInstance(
                "Do you want to delete ?",
                "No", "Yes"
        );
        dialog.show(fm, null);
        dialog.setOnFinishDialogListener(new ConfirmDialog.onFinishDialogListener() {
            @Override
            public void onFinishDialog(ConfirmDialog.BUTTONS button) {
                if (button == ConfirmDialog.BUTTONS.NEGATIVE) {
                    showToast("You cancel process");
                } else if (button == ConfirmDialog.BUTTONS.POSITIVE) {
                    showToast("Your process success");
                }
            }
        });
    }

    /******************************
     *  Listener Zone
     *****************************/
    View.OnClickListener btnIncomeExpenseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //callInputDialog();
            //callConfirmDialog();
            onIncomeExpenseClickListener listener =
                    (onIncomeExpenseClickListener) getActivity();
            listener.onIncomeExpenseClick();

        }
    };

    View.OnClickListener btnOkHttpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onOkHttpClickListener listener = (onOkHttpClickListener) getActivity();
            listener.onOkHttpClicked();
        }
    };

}
