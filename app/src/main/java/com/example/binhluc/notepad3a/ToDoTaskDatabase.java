package com.example.binhluc.notepad3a;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = ToDoTask.class, version = 1)
public abstract class ToDoTaskDatabase extends RoomDatabase {
    public abstract ToDoTaskDAO toDoTaskDAO();
    public static final String DATABASE_NAME = "ToDoTasksdb";
    public static ToDoTaskDatabase instance;

    public static ToDoTaskDatabase getInstance(Context context) {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,ToDoTaskDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
}

