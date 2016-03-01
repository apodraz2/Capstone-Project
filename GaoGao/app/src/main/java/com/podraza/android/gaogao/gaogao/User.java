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

    private String email;

    private String name;

    User(String name, String email) {
        dogs = new ArrayList<>();
        this.name = name;
        this.email = email;
    }

    User(String name, String email, ArrayList<ParcelableDog> dogs) {
        this.dogs = dogs;
        this.name = name;
        this.email = email;
    }

    User(Parcel in) {
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

    public void updateDogData(int page, String dogName) {
        if(dogName.equals(Utility.emptyString)) {
            if(page == 0 && dogs.size() != 0) {

                dogs.remove(page);

            } else if(page >= dogs.size() || dogs.size() == 0){

            } else {
                dogs.remove(page);
            }




        } else if (page == dogs.size()){

            ParcelableDog tempDog = new ParcelableDog(dogName);
            dogs.add(tempDog);

        } else {
            if ((page - 1) == dogs.size()) {
                ParcelableDog tempDog = new ParcelableDog(dogName);
                dogs.add(page - 1, tempDog);
            } else {
                ParcelableDog tempDog = dogs.get(page);
                tempDog.setName(dogName);

                dogs.remove(page);

                dogs.add(page, tempDog);
            }

        }


    }

    public void updateTodoData(int position, String todoDesc, int page) {

            //Case if user chose to delete item
            if (todoDesc.equals(" ")) {
                if(page == dogs.size() ) {

                } else {
                    dogs.get(page).getTodos().remove(position);

                }

            } else {
                //Case if user edited an item that was already in the list
                if (position != 100) {
                    ParcelableTodo tempTodo = dogs.get(page).getTodos().get(position);
                    tempTodo.setTodo(todoDesc);
                    dogs.get(page).getTodos().remove(position);
                    dogs.get(page).addTodos(tempTodo, position);

                }
                //Case if user created a new item to add to list
                else {
                    dogs.get(page - 1).getTodos().add(new ParcelableTodo(todoDesc, false));


                }
            }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(dogs);
        dest.writeString(name);
        dest.writeString(email);
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
