package com.example.adampodraza.myapplication.backend;

import java.util.HashMap;

/** The object model for the data we are sending through endpoints */
public class MyBean {
    long uid;


    public long getData() {
        return this.uid;
    }

    public void setData(long uid) {

        this.uid = uid;
    }
}