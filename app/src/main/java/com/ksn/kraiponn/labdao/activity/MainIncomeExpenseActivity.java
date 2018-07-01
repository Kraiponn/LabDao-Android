package com.ksn.kraiponn.labdao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ksn.kraiponn.labdao.R;

public class MainIncomeExpenseActivity extends AppCompatActivity {

    private ImageView btnScanning;
    private Button btnNewNote;
    private Button btnShowDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_income_expense);

        initInstance();
    }

    private void initInstance() {
        btnScanning = findViewById(R.id.button_scan);
        btnNewNote = findViewById(R.id.button_new_note);
        btnShowDetails = findViewById(R.id.button_show_details);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnScanning.setOnClickListener(btnScannClickListener);
        btnNewNote.setOnClickListener(btnNewNoteClickListener);
        btnShowDetails.setOnClickListener(btnShowDetailClickListener);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(
                    R.anim.slide_in_left, R.anim.slide_out_right
            );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            String res = data.getStringExtra("result_scan");
            Toast.makeText(this,
                    "Result " + res,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.slide_in_left, R.anim.slide_out_right
        );
    }


    private void showToast(String text) {
        Toast.makeText(this,
                text,
                Toast.LENGTH_SHORT).show();
    }


    /****************************
     *  Listener Zone
     */
    View.OnClickListener btnScannClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent itn = new Intent(
                    MainIncomeExpenseActivity.this,
                    ScanCodeActivity.class
            );
            startActivityForResult(itn, 999);
            overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        }
    };

    View.OnClickListener btnNewNoteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //showToast("New note Clicked");
            Intent itn = new Intent(
                    MainIncomeExpenseActivity.this,
                    IncomeExpenseActivity.class
            );
            startActivity(itn);
            overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        }
    };

    View.OnClickListener btnShowDetailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //showToast("Income Expense Clicked");
            Intent itn = new Intent(
                    MainIncomeExpenseActivity.this,
                    InputIcExpDataActivity.class
            );
            startActivity(itn);
            overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        }
    };


}
