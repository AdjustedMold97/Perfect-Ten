package com.example.homescreen.app;

import android.app.Application;

public class MyApplication extends Application {

    static private String username;

    static public String getUsername() {

        return username;

    }

    public static void setUsername(String username1) {

        username = username1;

    }

}