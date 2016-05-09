package com.podraza.android.gaogao.gaogao.widget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by adampodraza on 5/8/16.
 */
public class GaoGaoWidgetService extends RemoteViewsService {
    private String LOG_TAG = getClass().getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(LOG_TAG, "onGetViewFactory");

        return new GaoGaoWidgetViewsFactory(this.getApplicationContext(), intent);
    }
}
