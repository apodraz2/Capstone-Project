package com.podraza.android.gaogao.gaogao;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by adampodraza on 2/19/16.
 * Simple Java pojo that is parcelable
 */
public class ParcelableTodo implements Parcelable, Serializable {
    private String todo;
    private boolean done = false;

    public ParcelableTodo(String todo, boolean done) {
        this.todo = todo;
        this.done = done;
    }

    public ParcelableTodo(Parcel in) {
        String[] data = new String[1];

        in.readStringArray(data);

        this.todo = data[0];
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Unable to write both string and boolean? Check back later
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(todo);


    }

    /**
     * Creator fields
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ParcelableTodo createFromParcel(Parcel in) {
            return new ParcelableTodo(in);
        }

        public ParcelableTodo[] newArray(int size) {
            return new ParcelableTodo[size];
        }

    };
}