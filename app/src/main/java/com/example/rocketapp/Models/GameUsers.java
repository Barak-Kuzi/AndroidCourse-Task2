package com.example.rocketapp.Models;

import java.util.ArrayList;

public class GameUsers {

    private ArrayList<User> allUsers;

    public GameUsers() {}

    public ArrayList<User> getAllUsers() {
        if (this.allUsers == null)
            this.allUsers = new ArrayList<User>();

        return this.allUsers;
    }
}
