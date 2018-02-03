package com.example.matan.onesportapp.Cache;

import com.example.matan.onesportapp.Util.User;

public class UserCache {

    private static  User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User obj){
        user = obj;
    }

    public static void clear(){ user = null; }
}
