package com.example.binhluc.notepad3a;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.widget.CheckBox;
import android.widget.ImageView;

@Entity(tableName = "ToDoTask")
public class ToDoTask {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "text")
    private String ToDoTask_text;

    @ColumnInfo(name = "check")
    private  boolean check;
    // because room database cannot get boolean data type, I use int type.
    // 1 means check = true and vice versa.
    public ToDoTask(String ToDoTask_text)
    {
        this.ToDoTask_text = ToDoTask_text;
        this.check = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToDoTask_text() {
        return ToDoTask_text;
    }

    public void setToDoTask_text(String ToDoTask_text) {
        this.ToDoTask_text = ToDoTask_text;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

