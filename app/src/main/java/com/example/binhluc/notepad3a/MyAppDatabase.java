package com.example.binhluc.notepad3a;


import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import java.util.List;

@Database(entities = Note.class, version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract MyDAO myDAO();
    public static final String DATABASE_NAME = "notesdb";
    public static MyAppDatabase instance;

    public static MyAppDatabase getInstance(Context context) {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context, MyAppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
}