package com.podraza.android.gaogao.gaogao;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adampodraza on 2/23/16.
 */
public class ParcelableDog implements Parcelable, Serializable {
    private String LOG_TAG = getClass().getSimpleName();

    private ArrayList<ParcelableTodo> todos;
    private String name;

    ParcelableDog(String name) {
        this.todos = new ArrayList<ParcelableTodo>();
        this.name = name;
    }

    ParcelableDog(ArrayList<ParcelableTodo> todos, String name) {
        this.todos = todos;
        this.name = name;
    }

    ParcelableDog(Parcel in) {
        name = in.readString();

        todos = in.readArrayList(ParcelableTodo.class.getClassLoader());

    }

    public ArrayList<ParcelableTodo> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<ParcelableTodo> todos) {
        this.todos = todos;
    }

    public void addTodos(ParcelableTodo todo, int index) {
        this.todos.add(index, todo);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(todos);
        dest.writeString(name);

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
