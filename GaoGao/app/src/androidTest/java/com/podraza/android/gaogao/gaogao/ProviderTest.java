package com.podraza.android.gaogao.gaogao;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.podraza.android.gaogao.gaogao.data.DataContract;

/**
 * Created by adampodraza on 3/7/16.
 */
public class ProviderTest extends AndroidTestCase {

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                DataContract.UserEntry.CONTENT_URI,
                null,
                null
        );

        mContext.getContentResolver().delete(
                DataContract.DogEntry.CONTENT_URI,
                null,
                null
        );

        mContext.getContentResolver().delete(
                DataContract.TodoEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                DataContract.UserEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        long userId = 2;

        //TODO need to implement delete in provider
        //assertEquals("Error: records not deleted from provider", 0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(
                DataContract.DogEntry.buildDataUri(userId),
                null,
                null,
                null,
                null
        );

        assertEquals("Error: records not deleted from provider", 0, cursor.getCount());
        cursor.close();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecordsFromProvider();
    }
}
