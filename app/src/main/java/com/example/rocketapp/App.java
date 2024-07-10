package com.example.rocketapp;

import android.app.Application;

import com.example.rocketapp.Models.GameUsers;
import com.google.gson.Gson;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UserManagement.init(this);
        Gson myGson = new Gson();
        GameUsers allUsers = myGson.fromJson(UserManagement.getInstance().loadUsersFromDB(), GameUsers.class);

        if (allUsers == null) {
            allUsers = new GameUsers();
            UserManagement.getInstance().editDataInDB(myGson.toJson(allUsers));
        }
    }
}