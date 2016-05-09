package com.podraza.android.gaogao.gaogao;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.adampodraza.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by adampodraza on 5/9/16.
 */
public class CallCreateEndpointTask extends AsyncTask {
    private MyApi myApi;
    private Context mContext;
    private boolean mIsDogRequest;

    public CallCreateEndpointTask(Context context, boolean isDogRequest) {
        mContext = context;
        mIsDogRequest = isDogRequest;
    }


    @Override
    protected Void doInBackground(Object[] params) {

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

        String email = prefs.getString(Utility.userEmail, "none");

        try {
            if(mIsDogRequest) {
                myApi.addDog(email, (String) params[0]);
            } else {
                myApi.createTodo(email, (long) params[0], (String) params[1], (boolean) params[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
