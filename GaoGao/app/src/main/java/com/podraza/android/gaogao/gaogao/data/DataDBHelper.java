package com.podraza.android.gaogao.gaogao.data;

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
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + DataContract.UserEntry.TABLE_NAME + " (" +
                DataContract.UserEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                DataContract.UserEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                DataContract.UserEntry.COLUMN_EMAIL + " TEXT UNIQUE NOT NULL, " +
                " );";

        final String SQL_CREATE_DOG_TABLE = "CREATE TABLE " + DataContract.DogEntry.TABLE_NAME + " (" +
                DataContract.DogEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                DataContract.DogEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                " );";

        final String SQL_CREATE_TODO_TABLE = "CREATE TABLE " + DataContract.TodoEntry.TABLE_NAME + " (" +
                DataContract.TodoEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                DataContract.TodoEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                DataContract.TodoEntry.COLUMN_DONE + "BOOLEAN, " +
                " );";

        final String SQL_CREATE_USER_DOG_TABLE = "CREATE TABLE " + DataContract.UserDog.TABLE_NAME + " (" +
                DataContract.UserDog.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                DataContract.UserDog.COLUMN_DOG + " INTEGER NOT NULL, " +
                DataContract.UserDog.COLUMN_USER + " INTEGER NOT NULL, " +
                " );";

        final String SQL_CREATE_DOG_TODO_TABLE = "CREATE TABLE " + DataContract.DogTodo.TABLE_NAME + " (" +
                DataContract.DogTodo.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                DataContract.DogTodo.COLUMN_DOG + " INTEGER NOT NULL, " +
                DataContract.DogTodo.COLUMN_TODO + " INTEGER NOT NULL, " +
                " );";

        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_DOG_TABLE);
        db.execSQL(SQL_CREATE_TODO_TABLE);
        db.execSQL(SQL_CREATE_USER_DOG_TABLE);
        db.execSQL(SQL_CREATE_DOG_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.UserEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.DogEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.TodoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.UserDog.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.DogTodo.TABLE_NAME);
        onCreate(db);
    }
}
