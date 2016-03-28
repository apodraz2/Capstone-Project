package com.example.adampodraza.myapplication.backend;

import com.google.appengine.api.datastore.Key;

import java.util.HashMap;

/** The object model for the data we are sending through endpoints */
public class MyBean {
    Key uid;


    public Key getData() {
        return this.uid;
    }

    public void setData(Key uid) {

        this.uid = uid;
    }
}