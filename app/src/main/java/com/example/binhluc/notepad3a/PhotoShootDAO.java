package com.example.binhluc.notepad3a;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PhotoShootDAO {

    @Insert
    void addNote(PhotoShoot note);

    @Query(" SELECT * FROM photoshoots ")
    List<PhotoShoot> getAllNote();

    @Delete
    void delete(PhotoShoot note);

    @Query(" SELECT * FROM photoshoots WHERE id = :note_id ")
    PhotoShoot getNodeById(int note_id);

    @Query("SELECT * FROM photoshoots WHERE title LIKE :searchWord")
    List<PhotoShoot> getPhotoShootListSearch(String searchWord);

    @Update
    void Update(PhotoShoot note);
}
