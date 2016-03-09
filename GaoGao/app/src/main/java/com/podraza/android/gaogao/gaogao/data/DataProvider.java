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

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_USER + "/#", USER);
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


            case DOG: {
                Log.d(LOG_TAG, "dog query");
                long id = DataContract.getIdFromUri(uri);
                String selectQuery = "SELECT d.* FROM " + DataContract.DogEntry.TABLE_NAME + " d " + "JOIN " +
                        DataContract.UserDog.TABLE_NAME + " ud " + "ON " + DataContract.UserDog.COLUMN_DOG + " = d.id" +
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

                String selectQuery = "SELECT t.* FROM " + DataContract.TodoEntry.TABLE_NAME + " t " + "JOIN " +
                        DataContract.DogTodo.TABLE_NAME + " td " + "ON " + DataContract.DogTodo.COLUMN_TODO + " = t.id" +
                        " JOIN " + DataContract.DogEntry.TABLE_NAME + " u ON " + id + " = td.dog";

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
                long userId = DataContract.getIdFromUri(uri);


                long dogId = db.insertWithOnConflict(DataContract.DogEntry.TABLE_NAME, null, values, 0);
                ContentValues userDogValues = new ContentValues();
                userDogValues.put(DataContract.UserDog.COLUMN_USER, userId);
                userDogValues.put(DataContract.UserDog.COLUMN_DOG, values.getAsLong(DataContract.DogEntry.COLUMN_ID));

                long userDogId = db.insert(DataContract.UserDog.TABLE_NAME, null, userDogValues);

                Log.d(LOG_TAG, values.getAsString(DataContract.DogEntry.COLUMN_NAME));



                if(dogId > 0 && userDogId > 0) {
                    returnUri = DataContract.DogEntry.buildDataUri(dogId);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;

            }

            case TODO: {

                Log.d(LOG_TAG, "insert todo case");

                long dogId = DataContract.getIdFromUri(uri);
                long todoId = db.insertWithOnConflict(DataContract.TodoEntry.TABLE_NAME, null, values, 0);
                ContentValues userDogValues = new ContentValues();
                userDogValues.put(DataContract.DogTodo.COLUMN_DOG, dogId);
                userDogValues.put(DataContract.DogTodo.COLUMN_TODO, values.getAsLong(DataContract.TodoEntry.COLUMN_ID));

                long dogTodoId = db.insert(DataContract.DogTodo.TABLE_NAME, null, userDogValues);

                Log.d(LOG_TAG, "todoId is " + todoId);
                Log.d(LOG_TAG, "dogTodoId " + dogTodoId);

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

    /**
     * Simply use the id attribute of each item
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
                long id = DataContract.getIdFromUri(uri);
                db.delete(DataContract.UserEntry.TABLE_NAME, id + " = " + DataContract.UserEntry.COLUMN_ID, null);
                db.delete(DataContract.UserDog.TABLE_NAME, id + " = " + DataContract.UserDog.COLUMN_USER, null);
                return 1;

            }
            case DOG: {
                long id = DataContract.getIdFromUri(uri);


                db.delete(DataContract.DogEntry.TABLE_NAME, id + " = " + DataContract.DogEntry.COLUMN_ID, null);
                db.delete(DataContract.UserDog.TABLE_NAME, id + " = " + DataContract.UserDog.COLUMN_DOG, null);

                return 1;


            }
            case TODO: {

                long id = DataContract.getIdFromUri(uri);

                db.delete(DataContract.TodoEntry.TABLE_NAME, id + " = " + DataContract.TodoEntry.COLUMN_ID, null);
                db.delete(DataContract.DogTodo.TABLE_NAME, id + " = " + DataContract.DogTodo.COLUMN_TODO, null);

                return 1;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);



        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //TODO: implement method

        return 0;
    }
}
