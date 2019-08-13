package com.example.binhluc.notepad3a;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MyDAO {

    @Insert
    void addNote(Note note);

    @Query(" SELECT * FROM Notes ")
    List<Note> getAllNote();

    @Delete
    void delete(Note note);

    @Query(" SELECT * FROM Notes WHERE id = :note_id ")
    Note getNodeById(int note_id);

    @Query("SELECT * FROM Notes WHERE title LIKE :searchWord")
    List<Note> getNotesListSearch(String searchWord);

    @Update
    void Update(Note note);
}

