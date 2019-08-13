package com.example.binhluc.notepad3a;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ToDoTaskEdit extends AppCompatActivity {
    private EditText txt_activity;
    private Button btn_add;
    private ToDoTaskDAO dao;
    private ToDoTask chosenTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todotask_edit);

        txt_activity = findViewById(R.id.editText_activity);
        btn_add = findViewById(R.id.button_Add);
        dao = ToDoTaskDatabase.getInstance(this).toDoTaskDAO();

        // get the current note
        final Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            String chosenNoteId = intent.getStringExtra("CHOSEN_NOTE_ID");
            int id = Integer.parseInt(chosenNoteId);
            chosenTask = dao.getTaskById(id);
            txt_activity.setText(chosenTask.getToDoTask_text());
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddTask();
            }
        });
    }

    private void onAddTask()
    {
        String text = txt_activity.getText().toString();
        if(!text.isEmpty())
        {
            if(chosenTask!=null) {
                chosenTask.setToDoTask_text(text);
                dao.Update(chosenTask);
            }
            else {
                chosenTask = new ToDoTask(text);
                dao.addTask(chosenTask);
            }
            finish();
        }
        else
            Toast.makeText(this,"Please fill in the blank", Toast.LENGTH_SHORT).show();
    }
}
