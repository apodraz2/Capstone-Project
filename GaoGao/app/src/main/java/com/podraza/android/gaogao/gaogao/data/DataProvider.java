package com.podraza.android.gaogao.gaogao.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by adampodraza on 3/1/16.
 */
public class DataProvider extends ContentProvider {
    private final String LOG_TAG = this.getClass().getSimpleName();

    DataDBHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int USER = 100;
    static final int DOG = 101;
    static final int TODO = 102;
    static final int USER_DOG = 103;
    static final int DOG_TODO = 104;

    //private static final SQLiteQueryBuilder userDogQueryBuilder;
    //private static final SQLiteQueryBuilder dogTodoQueryBuilder;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_USER + "/#", USER);
        matcher.addURI(authority, DataContract.PATH_USER, USER);
        matcher.addURI(authority, DataContract.PATH_DOG + "/#", DOG);
        matcher.addURI(authority, DataContract.PATH_TODO + "/#", TODO);
        matcher.addURI(authority, DataContract.PATH_USER_DOG + "/#", USER_DOG);
        matcher.addURI(authority, DataContract.PATH_DOG_TODO + "/#", DOG_TODO);

        return matcher;
    }

    static {

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DataDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case USER:
                //long id = DataContract.getIdFromUri(uri);

                String selectQuery = "SELECT * FROM " + DataContract.UserEntry.TABLE_NAME;

                retCursor = mOpenHelper.getReadableDatabase().rawQuery(
                        selectQuery,
                        null
                );
                break;

            case DOG:
                long id = DataContract.getIdFromUri(uri);
                selectQuery = "SELECT * FROM " + DataContract.DogEntry.TABLE_NAME + " de, "
                        + DataContract.UserEntry.TABLE_NAME + " ue, " + DataContract.UserDog.TABLE_NAME + " ud WHERE ue."
                        + DataContract.UserEntry.COLUMN_ID + " = '" + id + "'" + " AND ue." + DataContract.UserEntry.COLUMN_ID
                        + " = " + "ud." + DataContract.UserEntry.COLUMN_ID + " AND de." + DataContract.DogEntry.COLUMN_ID + " = "
                        + "ud." + DataContract.UserEntry.COLUMN_ID;

                retCursor = mOpenHelper.getReadableDatabase().rawQuery(
                        selectQuery,
                        null
                );
                break;

            case TODO:
                id = DataContract.getIdFromUri(uri);

                selectQuery = "SELECT * FROM " + DataContract.TodoEntry.TABLE_NAME + " te, "
                        + DataContract.DogEntry.TABLE_NAME + " de, " + DataContract.DogTodo.TABLE_NAME + " dt WHERE de."
                        + DataContract.DogEntry.COLUMN_ID + " = '" + id + "'" + " AND ue." + DataContract.DogEntry.COLUMN_ID
                        + " = " + "dt." + DataContract.DogEntry.COLUMN_ID + " AND te." + DataContract.TodoEntry.COLUMN_ID + " = "
                        + "dt." + DataContract.DogEntry.COLUMN_ID;

                retCursor = mOpenHelper.getReadableDatabase().rawQuery(
                        selectQuery,
                        null
                );

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch(match) {
            case USER:
                return DataContract.UserEntry.CONTENT_ITEM_TYPE;
            case DOG:
                return DataContract.DogEntry.CONTENT_ITEM_TYPE;
            case TODO:
                return DataContract.TodoEntry.CONTENT_ITEM_TYPE;
            case USER_DOG:
                return DataContract.UserDog.CONTENT_ITEM_TYPE;
            case DOG_TODO:
                return DataContract.DogTodo.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch(match) {
            case USER: {
                long id = db.insertWithOnConflict(DataContract.UserEntry.TABLE_NAME, null, values, 0);

                Log.d(LOG_TAG, "id is " + id);



                if(id >= 0) {
                    returnUri = DataContract.UserEntry.buildDataUri(id);
                }else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;

            }

            case DOG: {
                long dogId = db.insert(DataContract.DogEntry.TABLE_NAME, null, values);
                ContentValues userDogValues = new ContentValues();
                userDogValues.put(DataContract.UserDog.COLUMN_USER, DataContract.getIdFromUri(uri));
                userDogValues.put(DataContract.UserDog.COLUMN_DOG, dogId);

                long userDogId = db.insert(DataContract.UserDog.TABLE_NAME, null, userDogValues);

                if(dogId > 0 && userDogId > 0) {
                    returnUri = DataContract.DogEntry.buildDataUri(dogId);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;

            }

            case TODO: {
                long todoId = db.insert(DataContract.TodoEntry.TABLE_NAME, null, values);
                ContentValues userDogValues = new ContentValues();
                userDogValues.put(DataContract.DogTodo.COLUMN_DOG, DataContract.getIdFromUri(uri));
                userDogValues.put(DataContract.DogTodo.COLUMN_TODO, todoId);

                long dogTodoId = db.insert(DataContract.DogTodo.TABLE_NAME, null, userDogValues);

                if(todoId > 0 && dogTodoId > 0) {
                    returnUri = DataContract.TodoEntry.buildDataUri(todoId);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //TODO: implement method

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //TODO: implement method

        return 0;
    }
}
