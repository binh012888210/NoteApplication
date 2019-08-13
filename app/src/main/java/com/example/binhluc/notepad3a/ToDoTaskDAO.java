package com.example.binhluc.notepad3a;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.widget.LinearLayout;

import java.util.List;

@Dao
public interface ToDoTaskDAO {
    @Insert
    void addTask(ToDoTask task);

    @Query(" SELECT * FROM ToDoTask ")
    List<ToDoTask> getallTask();

    @Delete
    void Delete(ToDoTask task);

    @Update
    void Update(ToDoTask task);

    @Query(" SELECT * FROM ToDoTask WHERE id = :task_id ")
    ToDoTask getTaskById(int task_id);

    @Query("SELECT * FROM ToDoTask WHERE text LIKE :searchWord")
    List<ToDoTask> getToDoListSearch(String searchWord);
}
