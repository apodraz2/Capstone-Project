package com.podraza.android.gaogao.gaogao;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by adampodraza on 2/25/16.
 */
public class Utility {
    public static String isDogResult = "is_dog_result";
    public static String isEditDog = "edit";
    public static String isEditTodo = "todo";
    public static String page = "page";
    public static String position = "position";
    public static String emptyString = " ";
    public static String arrayListIdentifier = "dogs";
    public static String dogId = "dog_id";
    public static String todoId = "todo_id";


    public static boolean verifyUserInput(String s) {

        if(s == null || s.equals("")) {

            return false;

        } else {
            for(char c: s.toCharArray()) {
                if(!Character.isSpaceChar(c) && !Character.isLetter(c) && !Character.isDigit(c)) {

                    return false;

                }
            }
        }

        return true;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
