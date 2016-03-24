package com.podraza.android.gaogao.gaogao;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;

/**
 * Created by adampodraza on 3/24/16.
 */
public class CoreApp extends Application {
    private static CoreApp me;

    public CoreApp() {
        me = this;
    }

    public static Context Context() {
        return me;
    }

    public static ContentResolver ContentResolver() {
        return me.getContentResolver();
    }
}
