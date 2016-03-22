package com.podraza.android.gaogao.gaogao.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
    static final int USER_EMAIL = 200;
    static final int DOG = 101;
    static final int TODO = 102;
    static final int USER_DOG = 103;
    static final int DOG_TODO = 104;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_USER + "/#", USER);
        matcher.addURI(authority, DataContract.PATH_USER + "/*", USER_EMAIL);
        matcher.addURI(authority, DataContract.PATH_DOG + "/#", DOG);
        matcher.addURI(authority, DataContract.PATH_TODO + "/#", TODO);
        matcher.addURI(authority, DataContract.PATH_USER, USER);
        matcher.addURI(authority, DataContract.PATH_DOG, DOG);
        matcher.addURI(authority, DataContract.PATH_TODO, TODO);

        return matcher;
    }

    static {

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new DataDBHelper(getContext());

        //mOpenHelper.onUpgrade(mOpenHelper.getWritableDatabase(), 2, 2);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        int match = sUriMatcher.match(uri);
        Log.d("LOG_TAG", "match is " + match);


        switch (match) {

            case USER: {
                //long id = DataContract.getIdFromUri(uri);

                String selectQuery = "SELECT * FROM " + DataContract.UserEntry.TABLE_NAME;

                retCursor = mOpenHelper.getReadableDatabase().rawQuery(
                        selectQuery,
                        null
                );
                break;
            }

            case USER_EMAIL: {
                String email = DataContract.getEmailFromUri(uri);

                String selectQuery = "SELECT * FROM " + DataContract.UserEntry.TABLE_NAME +
                        " WHERE " + DataContract.UserEntry.COLUMN_EMAIL + " =\'" + email+"\'";

                retCursor = mOpenHelper.getReadableDatabase().rawQuery(
                        selectQuery,
                        null
                );
                break;
            }


            case DOG: {
                Log.d(LOG_TAG, "dog query");
                long id = DataContract.getIdFromUri(uri);
                String selectQuery = "SELECT d.* FROM " + DataContract.DogEntry.TABLE_NAME + " d " + "JOIN " +
                        DataContract.UserDog.TABLE_NAME + " ud " + "ON " + DataContract.UserDog.COLUMN_DOG + " = d._id" +
                        " JOIN " + DataContract.UserEntry.TABLE_NAME + " u ON " + id + " = ud.user";

                retCursor = mOpenHelper.getReadableDatabase().rawQuery(
                        selectQuery,
                        null
                );

                break;
            }


            case TODO: {
                Log.d(LOG_TAG, "todo query");
                long id = DataContract.getIdFromUri(uri);

                String selectQuery = "SELECT * FROM " + DataContract.TodoEntry.TABLE_NAME +
                        " WHERE " + DataContract.TodoEntry.COLUMN_DOG_ID + " = " + id;

                retCursor = mOpenHelper.getReadableDatabase().rawQuery(
                        selectQuery,
                        null
                );

                Log.d(LOG_TAG, "count is " + retCursor.getCount());
                break;

            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

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

    /**
     * Users are identified by their id's
     * Dogs are identified by the mapping table where their id's match the user's id
     * Todos are identified by their individual foreign keys matching the id's of the provided dog id
     * @param uri
     * @param values
     * @return
     */
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch(match) {
            case USER: {
                db.beginTransaction();

                try {
                    long id = db.insertWithOnConflict(DataContract.UserEntry.TABLE_NAME, null, values, 0);

                    Log.d(LOG_TAG, "id is " + id);


                    if (id >= 0) {
                        returnUri = DataContract.UserEntry.buildDataUri(id);

                        getContext().getContentResolver().notifyChange(uri, null);
                    } else {
                        throw new SQLException("Failed to insert row into " + uri);
                    }
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }
                break;

            }

            case DOG: {
                db.beginTransaction();

                try {
                    long userId = DataContract.getIdFromUri(uri);

                    Log.d(LOG_TAG, "dog id is " + values.get(DataContract.DogEntry._id));

                    long dogId = db.insertWithOnConflict(DataContract.DogEntry.TABLE_NAME, null, values, 0);
                    ContentValues userDogValues = new ContentValues();
                    userDogValues.put(DataContract.UserDog.COLUMN_USER, userId);
                    userDogValues.put(DataContract.UserDog.COLUMN_DOG, values.getAsLong(DataContract.DogEntry._id));

                    long userDogId = db.insert(DataContract.UserDog.TABLE_NAME, null, userDogValues);

                    Log.d(LOG_TAG, values.getAsString(DataContract.DogEntry.COLUMN_NAME));


                    if (dogId > 0 && userDogId > 0) {
                        returnUri = DataContract.DogEntry.buildDataUri(dogId);
                        getContext().getContentResolver().notifyChange(uri, null);
                    } else {
                        throw new SQLException("Failed to insert row into " + uri);
                    }
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }
                break;

            }

            case TODO: {

                db.beginTransaction();

                try {
                    long todoId = db.insertWithOnConflict(DataContract.TodoEntry.TABLE_NAME, null, values, 0);


                    if (todoId > 0) {
                        returnUri = DataContract.TodoEntry.buildDataUri(todoId);
                        getContext().getContentResolver().notifyChange(uri, null);
                    } else {
                        throw new SQLException("Failed to insert row into " + uri);
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return returnUri;
    }

    /**
     * Simply use the id attribute of each item to locate within database
     * Try catches surround each transaction to avoid crashes.
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //TODO: implement method
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case USER: {
                db.beginTransaction();
                long id = DataContract.getIdFromUri(uri);

                try {
                    int deleted = db.delete(DataContract.UserEntry.TABLE_NAME, id + " = " + DataContract.UserEntry._id, null);

                    if(deleted >= 0) {
                        db.setTransactionSuccessful();
                        getContext().getContentResolver().notifyChange(uri, null);
                    } else {
                        throw new SQLiteException("Item was not deleted");
                    }
                } finally {
                    db.endTransaction();
                }

                return 1;

            }
            case DOG: {
                db.beginTransaction();
                long id = DataContract.getIdFromUri(uri);

                try {
                    int deleted = db.delete(DataContract.DogEntry.TABLE_NAME, id + " = " + DataContract.DogEntry._id, null);

                    if(deleted >= 0) {
                        db.setTransactionSuccessful();
                        getContext().getContentResolver().notifyChange(uri, null);
                    } else {
                        throw new SQLiteException("Item was not deleted");
                    }
                } finally {
                    db.endTransaction();
                }


                return 1;


            }
            case TODO: {
                db.beginTransaction();

                long id = DataContract.getIdFromUri(uri);

                try {

                    int deleted = db.delete(DataContract.TodoEntry.TABLE_NAME, id + " = " + DataContract.TodoEntry._id, null);

                    if(deleted >= 0) {
                        db.setTransactionSuccessful();
                        getContext().getContentResolver().notifyChange(uri, null);
                    } else {
                        throw new SQLiteException("Item was not deleted");
                    }

                } finally {
                    db.endTransaction();
                }

                return 1;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);



        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //TODO: implement method

        final int match = sUriMatcher.match(uri);
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final long id = DataContract.getIdFromUri(uri);

        switch(match) {

            case USER: {
                db.beginTransaction();

                try {

                    db.updateWithOnConflict(DataContract.UserEntry.TABLE_NAME, values, id + " = " + DataContract.UserEntry._id, null, 0);
                    getContext().getContentResolver().notifyChange(uri, null);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                return 1;

            }

            case DOG: {

                db.beginTransaction();

                try {

                    db.updateWithOnConflict(DataContract.DogEntry.TABLE_NAME, values, id + " = " + DataContract.DogEntry._id, null, 0);
                    getContext().getContentResolver().notifyChange(uri, null);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                return 1;

            }

            case TODO: {

                db.beginTransaction();

                try {


                    db.updateWithOnConflict(DataContract.TodoEntry.TABLE_NAME, values, id + " = " + DataContract.TodoEntry._id, null, 0);
                    getContext().getContentResolver().notifyChange(uri, null);
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                return 1;

            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }



        }


    }
}
