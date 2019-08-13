package com.example.binhluc.notepad3a;

public interface onToDoTaskClickListener {
    void onToDoTaskClick(int todotaskid);
    void onToDoTaskClickDeleteTask(int todotaskid);
    void onToDoTaskChecked(boolean isChecked, int todotaskid);
}
