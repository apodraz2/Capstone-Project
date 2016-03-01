package com.podraza.android.gaogao.gaogao.content_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adampodraza on 3/1/16.
 */
public class DataDBHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "data.db";


    public DataDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DataContract.UserEntry.TABLE_NAME + " (" +
               // DataContract.UserEntry._ID + " INTEGER PRIMARY KEY," +


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
