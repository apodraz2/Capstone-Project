package com.podraza.android.gaogao.gaogao;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by adampodraza on 3/1/16.
 */
public class User implements Parcelable, Serializable {
    private ArrayList<ParcelableDog> dogs;

    private long id;

    private String email;

    private String name;

    public User(){
        dogs = new ArrayList<ParcelableDog>();
    }

    //new user
    public User(String name, String email) {
        this.id = this.hashCode();
        dogs = new ArrayList<>();
        this.name = name;
        this.email = email;
    }

    //reinstantiate an existing user
    User(long id, String name, String email, ArrayList<ParcelableDog> dogs) {
        this.id = id;
        this.dogs = dogs;
        this.name = name;
        this.email = email;
    }

    User(Parcel in) {
        this.id = in.readLong();
        this.email = in.readString();
        this.name = in.readString();
        this.dogs = in.readArrayList(ParcelableDog.class.getClassLoader());
    }

    public ArrayList<ParcelableDog> getDogs() {
        return dogs;
    }

    public void setDogs(ArrayList<ParcelableDog> dogs) {
        this.dogs = dogs;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(long id) {this.id = id;}

    public long getId() {return this.id;}



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(dogs);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeLong(id);
    }

    /**
     * Creator fields
     */
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }

    };
}
