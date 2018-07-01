package com.ksn.kraiponn.labdao.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ksn.kraiponn.labdao.R;
import com.ksn.kraiponn.labdao.dao.IncomeExpenseDBHelper;
import com.ksn.kraiponn.labdao.dialog.CustomDatePicker;
import com.ksn.kraiponn.labdao.dialog.ItemDialog;

public class InputIcExpDataActivity extends AppCompatActivity {

    /****************************
     *  Variable Zone
     ***************************/
    private Toolbar toolbar;
    private RadioButton radIncome;
    private RadioButton radExpense;
    private EditText edtDate;
    private EditText edtTitleList;
    private EditText edtAmount;
    private ImageView imgDate;
    private ImageView imgTitleList;
    private Button btnSave;

    private CustomDatePicker mDatePicker;
    private IncomeExpenseDBHelper helper;
    private SQLiteDatabase db;
    private String m_id = "";
    private int mDate = 0;
    private int mMonth = 0;
    private int mYear = 0;
    private String mTitle = "";
    private String mtype = "";
    private int mAmount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ic_exp_data);

        initInstance();
    }

    private void initInstance() {
        toolbar = findViewById(R.id.toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        radIncome = findViewById(R.id.radio_income);
        radExpense = findViewById(R.id.radio_expense);
        imgDate = findViewById(R.id.image_date);
        imgTitleList = findViewById(R.id.image_titleList);
        edtDate = findViewById(R.id.edit_date);
        edtTitleList = findViewById(R.id.edit_titleList);
        edtAmount = findViewById(R.id.edit_amount);
        btnSave = findViewById(R.id.button_save);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgDate.setOnClickListener(imgDateClickListener);
        imgTitleList.setOnClickListener(imgTitleClickListener);
        btnSave.setOnClickListener(btnSaveClickListener);
    }


    @Override
    protected void onStart() {
        super.onStart();
        helper = IncomeExpenseDBHelper.newInstance(this);
        db = helper.getWritableDatabase();

        Intent itn = getIntent();
        if (itn.getStringExtra("_id") != null) {
            m_id = itn.getStringExtra("_id");
            readDataFromDB();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(
                R.anim.slide_in_left, R.anim.slide_out_right
        );
    }


    /****************************
     *  Custom Method Zone
     ***************************/
    private void showToast(String text) {
        Toast.makeText(this,
                text, Toast.LENGTH_SHORT).show();
    }

    private void testGetAllData() {
        String sql = "SELECT * FROM income_expense ";
        Cursor cursor = db.rawQuery(sql, null);
        String str = "";
        while (cursor.moveToNext()) {
            str += "_id " + cursor.getInt(0);
            str += "\n date " + cursor.getInt(1);
            str += "\n month " + cursor.getInt(2);
            str += "\n year " + cursor.getInt(3);
            str += "\n title " + cursor.getString(4);
            str += "\n type " + cursor.getString(5);
            str += "\n amount " + cursor.getInt(6);
        }

        showToast(str);
    }

    private void readDataFromDB() {
        m_id = getIntent().getStringExtra("_id");
        String sql = "SELECT * FROM income_expense WHERE _id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{m_id});

        // _id(0), date(1), month(2), year(3), title(4), type(5), amount(6)
        while (cursor.moveToNext()) {
            if (cursor.getString(5).equals("+")) {
                radIncome.setChecked(true);
            } else {
                radExpense.setChecked(true);
            }

            String dd = ((cursor.getInt(1) < 10 ? "0" : ""))
                    + cursor.getInt(1);
            String mm = ((cursor.getInt(2) < 10 ? "0" : ""))
                    + cursor.getInt(2);
            int yy = cursor.getInt(3);
            String dmy = dd + "-" + mm + "-" + yy;
            edtDate.setText(dmy);
            edtTitleList.setText(cursor.getString(4));
            edtAmount.setText(cursor.getString(6));
        }

        cursor.close();
    }

    private void readDataFromView() {
        String errMsg = "";
        if (!radIncome.isChecked() && !radExpense.isChecked()) {
            errMsg = "กรุณาเลือกประเภทรายการ";
        } else if (edtDate.getText().toString().isEmpty()) {
            errMsg = "กรุณาเลือกวันที่ในการบันทึกรายการ";
        } else if (edtTitleList.getText().toString().isEmpty()) {
            errMsg = "กรุณาระบุชื่อรายการ สำรับบันทึก";
        } else if (edtAmount.getText().toString().isEmpty()) {
            errMsg = "กรุณา ระบุจำนวนเงิน";
        }

        if (!errMsg.equals("")) {
            showToast(errMsg);
            return;
        }

        String[] dateMonth = edtDate.getText().toString().split("-");
        mDate = Integer.valueOf(dateMonth[0]);
        mMonth = Integer.valueOf(dateMonth[1]);
        mYear = Integer.valueOf(dateMonth[2]);
        mTitle = edtTitleList.getText().toString().trim();
        double am = Double.valueOf(edtAmount.getText().toString().trim());
        mAmount = (int) am;

        if (radIncome.isChecked()) {
            mtype = "+";
        } else {
            mtype = "-";
        }

        ContentValues cv = new ContentValues();
        cv.put("date", mDate);
        cv.put("month", mMonth);
        cv.put("year", mYear);
        cv.put("title", mTitle);
        cv.put("type", mtype);
        cv.put("amount", mAmount);

        try {
            if (m_id.equals("")) {
                db.insert("income_expense", null, cv);
                showToast("Add new item successfully");
            } else {
                db.update(
                        "income_expense",
                        cv,
                        "_id = ?",
                        new String[]{m_id}
                );
                showToast("Edit item successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        radIncome.setChecked(true);
        edtDate.setText("");
        edtTitleList.setText("");
        edtAmount.setText("");
        m_id = "";
    }

    /****************************
     *  Listener Zone
     ***************************/
    View.OnClickListener imgDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            mDatePicker = CustomDatePicker.newInstance();
            mDatePicker.show(fm, null);
            mDatePicker.setOnItemSelectListener(new CustomDatePicker.onFinishDialogListener() {
                @Override
                public void onFinishDialog(String[] dateRes) {
                    String str = dateRes[0] + "-" + dateRes[1] + "-" + dateRes[2];
                    edtDate.setText(str);
                }
            });
        }
    };

    View.OnClickListener imgTitleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getSupportFragmentManager();
            final String[] items;
            String[] income_item = {"เงินเดือน", "รายได้พิเศษ", "โบนัส"};
            String[] expense_item = {
                    "ค่าบ้าน", "ค่าน้ำมันรถ", "ค่าอาหาร", "ค่าเสื้อผ้า",
                    "ค่าอุปกรณ์", "ค่าเลี้ยงลูก", "ค่าเลี้ยงดูพ่อ-แม่"
            };

            if (radIncome.isChecked()) {
                items = income_item;
            } else if (radExpense.isChecked()) {
                items = expense_item;
            } else {
                items = income_item;
            }

            ItemDialog dialog = ItemDialog.newInstance(
                    "กรุณาเลือกรายการ ", items
            );
            dialog.show(fm, null);
            dialog.setOnFinishDialogListener(new ItemDialog.onFinishDialogListener() {
                @Override
                public void onFinishDialog(int selectIndex) {
                    if (selectIndex < 0) {
                        return;
                    }
                    edtTitleList.setText(items[selectIndex]);
                }
            });
        }
    };


    View.OnClickListener btnSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            readDataFromView();
            //testGetAllData();
        }
    };

}
