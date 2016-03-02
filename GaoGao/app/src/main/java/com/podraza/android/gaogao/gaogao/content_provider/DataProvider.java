package com.podraza.android.gaogao.gaogao.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by adampodraza on 3/1/16.
 */
public class DataProvider extends ContentProvider {

    DataDBHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int USER = 100;
    static final int DOG = 101;
    static final int TODO = 102;
    static final int USER_DOG = 103;
    static final int DOG_TODO = 104;

    private static final SQLiteQueryBuilder userDogQueryBuilder;
    private static final SQLiteQueryBuilder dogTodoQueryBuilder;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, DataContract.PATH_USER + "/#", USER);
        matcher.addURI(authority, DataContract.PATH_DOG + "/#", DOG);
        matcher.addURI(authority, DataContract.PATH_TODO + "/#", TODO);
        matcher.addURI(authority, DataContract.PATH_USER_DOG + "/#", USER_DOG);
        matcher.addURI(authority, DataContract.PATH_DOG_TODO + "/#", DOG_TODO);

        return matcher;
    }

    static {
        userDogQueryBuilder = new SQLiteQueryBuilder();

        userDogQueryBuilder.setTables(
                //TODO
                "HELLO"
        );
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
                long id = DataContract.getIdFromUri(uri);
                retCursor = SQLiteQueryBuilder.buildQueryString()
                break;
        }
        return null;
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
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
