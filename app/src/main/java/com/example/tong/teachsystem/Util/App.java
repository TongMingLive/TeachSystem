package com.example.tong.teachsystem.Util;

import android.app.Application;

import com.example.tong.teachsystem.Bean.User;

/**
 * Created by flytoyou on 2017/3/1.
 */

public class App extends Application {
    public static User user;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
