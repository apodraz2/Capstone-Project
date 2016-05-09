package com.podraza.android.gaogao.gaogao;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.adampodraza.myapplication.backend.myApi.MyApi;
import com.example.adampodraza.myapplication.backend.myApi.model.Key;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


/**
 * Created by adampodraza on 5/9/16.
 */
public class CallDestroyEndpointTask extends AsyncTask {
    private Context mContext;
    private boolean mIsDestroyDog;
    private MyApi myApi;

    public CallDestroyEndpointTask (Context context, boolean isDestroyDog) {
        mContext = context;
        mIsDestroyDog = isDestroyDog;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://gaogao-1257.appspot.com/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                        request.setDisableGZipContent(true);
                    }
                });

        myApi = builder.build();

        SharedPreferences prefs = mContext.getSharedPreferences("com.podraza.android.gaogao.gaogao", Context.MODE_PRIVATE);

        try {
            if(mIsDestroyDog) {
                myApi.deleteDog((Key) params[0]);
            } else {
                myApi.deleteTodo((Key) params[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
