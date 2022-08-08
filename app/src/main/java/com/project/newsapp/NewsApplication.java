package com.project.newsapp;

import android.app.Application;

import androidx.room.Room;

import com.project.newsapp.database.NewsAppDatabase;

//Application is the base class for maintaining global application state
public class NewsApplication extends Application {

    private static NewsAppDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        //Creates the database and initialize it.
        database = Room.databaseBuilder(this, NewsAppDatabase.class, "newsapp_db").build();
    }

    public static NewsAppDatabase getDatabase() {
        return database;
    }
}
