package com.ksn.kraiponn.labdao.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ksn.kraiponn.labdao.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomSpinnerActivity extends AppCompatActivity {
    private Spinner spnnMonth;
    private Spinner spnnYear;
    private ArrayAdapter<String> mAdapter;
    private String[] mMonthTH = {
            "January", "Febuary", "March",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_spinner);

        initInstance();
    }

    private void initInstance() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        spnnMonth = toolbar.findViewById(R.id.spinner_month);
        spnnYear = toolbar.findViewById(R.id.spinner_year);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Calendar cal = Calendar.getInstance();
        int crMonth = cal.get(Calendar.MONTH);
        int crYear = cal.get(Calendar.YEAR);

        mAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_custom_item, mMonthTH
        );
        spnnMonth.setAdapter(mAdapter);
        spnnMonth.setSelection(crMonth);

        ArrayList year = new ArrayList();
        for (int ly=0; ly<5; ly++) {
            year.add(crYear - ly);
        }

        mAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_custom_item, year
        );
        spnnYear.setAdapter(mAdapter);
        spnnYear.setSelection(0);

        spnnMonth.postDelayed(new Runnable() {
            @Override
            public void run() {
                spnnMonth.setDropDownVerticalOffset(spnnMonth.getHeight() + 5);
            }
        }, 500);
    }
}
