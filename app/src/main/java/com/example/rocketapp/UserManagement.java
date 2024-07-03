package com.example.rocketapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class UserManagement {
    @SuppressLint("StaticFieldLeak")
    private static UserManagement userManagement;
    private SharedPreferences myPreferences;
    private static final String DB_NAME = "highestScores";
    private static final String KEY_DB = "usersDB";
    private Context context;
    private GameUsers allUsers;
    private Gson myGson;

    private UserManagement(Context context) {
        this.myPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        this.context = context;
        this.myGson = new Gson();

    }

    public static UserManagement getInstance() {
        return userManagement;
    }


    public static void init(Context context) {
        //Initializes the UserManagement if it has not been initialized yet or if the context has changed.
        if (getInstance() == null || getInstance().context != context) {
            userManagement = new UserManagement(context.getApplicationContext());
        }
    }

    public void saveUserDetailsInDB(String userName, int scoreValue, double latitude, double longitude) {
        // Pulls the information from the DB and converts it from JSON to JAVA OBJ
        this.allUsers = myGson.fromJson(loadUsersFromDB(), GameUsers.class);
        // Adding the New User to the ArrayList
        this.allUsers.getAllUsers().add(new User(userName, scoreValue, latitude, longitude));
        // Sorting the userScores desc
        this.allUsers.getAllUsers().sort((user1, user2) -> user2.getScoreValue() - user1.getScoreValue());
        // If the list of scores exceeds 10 entries, the score at index 10 is removed (Element num 11).
        // This ensures that only the top 10 scores are kept.
        if (this.allUsers.getAllUsers().size() > 10)
            this.allUsers.getAllUsers().remove(10);

        editDataInDB(myGson.toJson(this.allUsers));
    }

    public String loadUsersFromDB() {
        return myPreferences.getString(KEY_DB, "");
    }

    public void editDataInDB(String data) {
        // A object is created to make changes into the SharedPreferences
        SharedPreferences.Editor editor = myPreferences.edit();
        // Converts the new array to JSON and saves in the DB asynchronously
        editor.putString(KEY_DB, data).apply();
    }

}
