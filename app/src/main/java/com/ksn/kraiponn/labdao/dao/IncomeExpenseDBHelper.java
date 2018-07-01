package com.ksn.kraiponn.labdao.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IncomeExpenseDBHelper extends SQLiteOpenHelper {
    private static IncomeExpenseDBHelper sHeloper;
    private static final String DB_NAME = "db_income_expense";
    private static final int VERSION = 1;

    private IncomeExpenseDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static IncomeExpenseDBHelper newInstance(Context context) {
        if (sHeloper == null) {
            sHeloper = new IncomeExpenseDBHelper(context.getApplicationContext());
        }
        return sHeloper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE income_expense(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " date INTEGER, " +
                " month INTEGER, " +
                " year INTEGER, " +
                " title TEXT, " +
                " type TEXT, " +
                " amount INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
