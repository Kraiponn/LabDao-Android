package com.ksn.kraiponn.labdao.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ksn.kraiponn.labdao.R;
import com.ksn.kraiponn.labdao.adapter.IncomeExpenseAdapter;
import com.ksn.kraiponn.labdao.dao.IncomeExpenseDBHelper;
import com.ksn.kraiponn.labdao.manager.IcExpChildItem;
import com.ksn.kraiponn.labdao.manager.IcExpSectionItem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class IncomeExpenseActivity extends AppCompatActivity {

    /****************************
     *  Variable Zone
     ***************************/
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private Spinner spnnMonth;
    private Spinner spnnYear;
    private RecyclerView rcv;
    private TextView tvTotalIncome;
    private TextView tvTotalExpense;
    private TextView tvBalance;

    private IncomeExpenseDBHelper helper;
    private SQLiteDatabase db;
    private IncomeExpenseAdapter mAdapter;
    private ArrayList mItem;
    private ArrayAdapter<String> mMYAdapter;
    private final String[] mMonthTH = {
            "มกราคม", "กุมภาพันธ์", "มีนาคม",
            "เมษายน", "พฤษภาคม", "มิถุนายน",
            "กรกฎาคม", "สิงหาคม", "กันยายน",
            "ตุลาคม", "พฤษจิกายน", "ธันวาคม"
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense);

        initInstance();
        helper = IncomeExpenseDBHelper.newInstance(this);
        db = helper.getWritableDatabase();
    }

    private void initInstance() {
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);
        spnnMonth = findViewById(R.id.spinner_month);
        spnnYear = findViewById(R.id.spinner_year);
        rcv = findViewById(R.id.recyclerView);
        tvTotalIncome = findViewById(R.id.text_totalIncome);
        tvTotalExpense = findViewById(R.id.text_totalExpense);
        tvBalance = findViewById(R.id.text_balance);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fab.setOnClickListener(fabClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH);
        int currentYear = cal.get(Calendar.YEAR);

        mMYAdapter = new ArrayAdapter<>(
                this, R.layout.spinner_custom_item, mMonthTH
        );
        spnnMonth.setAdapter(mMYAdapter);
        spnnMonth.setSelection(currentMonth);

        ArrayList year = new ArrayList();
        for (int i=0; i<5; i++) {
            year.add(currentYear - i);
        }
        mMYAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_custom_item,
                year
        );
        spnnYear.setAdapter(mMYAdapter);
        spnnYear.setSelection(0);

        spnnMonth.setOnItemSelectedListener(spnnMonthSelectListener);
        spnnYear.setOnItemSelectedListener(spnnYearItemSelect);

        createItem();
    }

    private void createItem() {
        String sql = "SELECT * FROM income_expense " +
                " WHERE month = ? AND year = ? " +
                " ORDER BY date ";

        int m = spnnMonth.getSelectedItemPosition() + 1;
        int y = Integer.valueOf(spnnYear.getSelectedItem().toString());
        //y -= 543;

        String[] args = {m + "", y + ""};
        Cursor cursor = db.rawQuery(sql, args);
        if (cursor == null) {
            showToast("Income_Expense Table is Emtrys");
            return;
        }

        int date = 0;
        int lastDate = 0;
        String dateMonth = "";
        int drawer = 0;
        String title = "";
        String type = "";
        int amount = 0;
        int _id = 0;
        int total_income = 0;
        int total_expense = 0;
        mItem = new ArrayList();

        while (cursor.moveToNext()) {
            _id = cursor.getInt(0);
            date = cursor.getInt(1);
            if (date != lastDate) {
                dateMonth = ((cursor.getInt(1) < 10 ? "0" : ""))
                            + cursor.getInt(1);
                dateMonth += "  " + mMonthTH[cursor.getInt(2) - 1];
                mItem.add(new IcExpSectionItem(dateMonth));
                lastDate = date;
            }

            title = cursor.getString(4);
            type = cursor.getString(5);
            /*String am = NumberFormat.getIntegerInstance()
                            .format(cursor.getInt(6));*/
            amount = cursor.getInt(6); //Integer.valueOf(am);

            if (type.equals("+")) {
                total_income += amount;
                drawer = R.drawable.ic_plus_circle;
            } else {
                total_expense += amount;
                drawer = R.drawable.ic_minus_circle;
            }

            mItem.add(new IcExpChildItem(
                    _id, drawer, title, String.valueOf(amount)
            ));
        }

        mAdapter = new IncomeExpenseAdapter(this, mItem);
        rcv.setAdapter(mAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        String ic = NumberFormat.getIntegerInstance().format(total_income);
        String exp = NumberFormat.getIntegerInstance().format(total_expense);
        String bl = NumberFormat.getIntegerInstance()
                .format(total_income - total_expense);
        tvTotalIncome.setText(ic);
        tvTotalExpense.setText(exp);
        tvBalance.setText(bl);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /****************************
     *  Custom Method Zone
     ***************************/
    private void showToast(String text) {
        Toast.makeText(this,
                text, Toast.LENGTH_SHORT).show();
    }




    /****************************
     *  Listener Zone
     ***************************/
    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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
    };

    AdapterView.OnItemSelectedListener spnnMonthSelectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    AdapterView.OnItemSelectedListener spnnYearItemSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}
