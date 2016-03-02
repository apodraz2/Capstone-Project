package com.podraza.android.gaogao.gaogao.content_provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by adampodraza on 3/1/16.
 */
public class DataContract {

    public static final String CONTENT_AUTHORITY = "com.podraza.android.gaogao.gaogao";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USER = "user";
    public static final String PATH_DOG = "dog";
    public static final String PATH_TODO = "todo";
    public static final String PATH_USER_DOG = "user_dog";
    public static final String PATH_DOG_TODO = "dog_todo";

    public static final class UserEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER;

        public static final String TABLE_NAME = "user";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";


        public static final String COLUMN_USER_SETTING = "user_setting";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class DogEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DOG).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DOG;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DOG;

        public static final String TABLE_NAME = "dog";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ID = "id";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class TodoEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TODO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TODO;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TODO;

        public static final String TABLE_NAME = "todo";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_DONE = "done";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class UserDog implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_DOG).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_DOG;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_DOG;

        public static final String TABLE_NAME = "user_dog";
        public static final String COLUMN_DOG = "dog";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_ID = "id";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class DogTodo implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DOG_TODO).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DOG_TODO;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DOG_TODO;

        public static final String TABLE_NAME = "user_todo";
        public static final String COLUMN_DOG = "dog";
        public static final String COLUMN_TODO = "todo";
        public static final String COLUMN_ID = "id";

        public static Uri buildDataUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static long getIdFromUri(Uri uri) {
        return Long.parseLong(uri.getPathSegments().get(1));
    }
}
