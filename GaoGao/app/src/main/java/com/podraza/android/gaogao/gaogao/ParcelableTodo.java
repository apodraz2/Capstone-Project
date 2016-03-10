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
    private long id;
    private long dogId;

    public ParcelableTodo(long id, String todo, boolean done, long dogId) {
        this.id = id;
        this.todo = todo;
        this.done = done;
        this.dogId = dogId;
    }

    public ParcelableTodo(String todo, long dogId) {
        this.id = this.hashCode();
        this.todo = todo;
        this.done = false;
        this.dogId = dogId;
    }

    public ParcelableTodo(Parcel in) {
        id = in.readLong();

        done = in.readByte() != 0;

        todo = in.readString();
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

    public long getId() {return this.id;}

    public void setId(long id) {
        this.id = id;
    }

    public long getDogId() {
        return dogId;
    }

    public void setDogId(long dogId) {
        this.dogId = dogId;
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

        dest.writeByte((byte) (done ? 1 : 0));

        dest.writeLong(id);

    }

    /**
     * Creator fields
     */
    public static final Parcelable.Creator<ParcelableTodo> CREATOR = new Parcelable.Creator<ParcelableTodo>() {
        public ParcelableTodo createFromParcel(Parcel in) {
            return new ParcelableTodo(in);
        }

        public ParcelableTodo[] newArray(int size) {
            return new ParcelableTodo[size];
        }

    };
}
