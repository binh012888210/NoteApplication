package com.example.binhluc.notepad3a;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = PhotoShoot.class, version = 1)
public abstract class PhotoShootDB extends RoomDatabase {
    public abstract PhotoShootDAO photoShootDAO();
    public static final String DATABASE_NAME = "photoshootsdb";
    public static PhotoShootDB instance;

    public static PhotoShootDB getInstance(Context context) {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context, PhotoShootDB.class, DATABASE_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
}