package com.podraza.android.gaogao.gaogao.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by adampodraza on 5/8/16.
 */
public class GaoGaoWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GaoGaoWidgetViewsFactory(this.getApplicationContext(), intent);
    }
}
