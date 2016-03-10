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
    private long id;

    public ParcelableDog(String name) {
        this.id = this.hashCode();
        this.todos = new ArrayList<ParcelableTodo>();
        this.name = name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    ParcelableDog(long id, ArrayList<ParcelableTodo> todos, String name) {
        this.id = id;
        this.todos = todos;
        this.name = name;
    }

    ParcelableDog(Parcel in) {
        id = in.readLong();

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

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(todos);
        dest.writeString(name);
        dest.writeLong(id);

    }

    /**
     * Creator fields
     */
    public static final Parcelable.Creator<ParcelableDog> CREATOR = new Parcelable.Creator<ParcelableDog>() {
        public ParcelableDog createFromParcel(Parcel in) {
            return new ParcelableDog(in);
        }

        public ParcelableDog[] newArray(int size) {
            return new ParcelableDog[size];
        }

    };
}
