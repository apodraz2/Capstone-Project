package com.podraza.android.gaogao.gaogao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.podraza.android.gaogao.gaogao.data.DataContract;
import com.podraza.android.gaogao.gaogao.data.DataDBHelper;

import java.util.HashSet;

/**
 * Created by adampodraza on 3/4/16.
 */
public class DataDBHelperTest extends AndroidTestCase {
    //TODO: Implement provider tests (test the DB helper here too)

    void deleteTheDatabase() {
        mContext.deleteDatabase(DataDBHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();

        tableNameHashSet.add(DataContract.UserEntry.TABLE_NAME);
        tableNameHashSet.add(DataContract.DogEntry.TABLE_NAME);
        tableNameHashSet.add(DataContract.TodoEntry.TABLE_NAME);

        mContext.deleteDatabase(DataDBHelper.DATABASE_NAME);

        SQLiteDatabase db = new DataDBHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        do {
            tableNameHashSet.remove(c.getString(0));
        } while(c.moveToNext());

        assertTrue("Error: Your database was created without all of the entry tables.", tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + DataContract.UserEntry.TABLE_NAME + ")", null);

        assertTrue("Error: This means that we were unable to query the database for table information.", c.moveToFirst());

        final HashSet<String> dogColumnHashSet = new HashSet<String>();

        dogColumnHashSet.add(DataContract.DogEntry.COLUMN_ID);
        dogColumnHashSet.add(DataContract.DogEntry.COLUMN_NAME);

        int columnNameIndex = c.getColumnIndex("name");

        do {
            String columnName = c.getString(columnNameIndex);
            dogColumnHashSet.remove(columnName);
        } while(c.moveToNext());

        assertTrue("Error: The database doesn't contain all of the required dog entry columns", dogColumnHashSet.isEmpty());

        db.close();
    }

    public void testUserTable() {
        long dogRowId = insertDog();

        assertFalse("Error: dog not inserted correctly", dogRowId == -1L);

        DataDBHelper dbHelper = new DataDBHelper(mContext);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues userValues = new ContentValues();
        userValues.put(DataContract.UserEntry.COLUMN_ID, 2);
        userValues.put(DataContract.UserEntry.COLUMN_EMAIL, "apodra86@gmail.com");
        userValues.put(DataContract.UserEntry.COLUMN_NAME, "Adam");

        long userRowId = db.insert(DataContract.UserEntry.TABLE_NAME, null, userValues);
        assertTrue(userRowId != -1);

        Cursor userCursor = db.query(
                DataContract.UserEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertTrue("Error: No records returned from user query", userCursor.moveToFirst());

        assertFalse("Error: more than one record returned from user query", userCursor.moveToNext());

        userCursor.close();
        dbHelper.close();


    }

    public long insertDog() {
        DataDBHelper dbHelper = new DataDBHelper(mContext);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues testValues = new ContentValues();
        testValues.put(DataContract.DogEntry.COLUMN_NAME, "Denver");
        testValues.put(DataContract.DogEntry.COLUMN_ID, 1);

        long dogRowId;
        dogRowId = db.insert(DataContract.DogEntry.TABLE_NAME, null, testValues);

        assertTrue(dogRowId != -1);

        Cursor cursor = db.query(
                DataContract.DogEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertTrue("Error: No Records returned from dog query", cursor.moveToFirst());

        assertFalse("Error: More than one record returned from dog query", cursor.moveToNext());

        cursor.close();
        db.close();
        return dogRowId;

    }

    //TODO test the todo table?

}
