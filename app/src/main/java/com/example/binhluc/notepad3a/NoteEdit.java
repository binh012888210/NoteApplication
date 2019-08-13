package com.example.binhluc.notepad3a;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteEdit extends AppCompatActivity {
    private EditText txt_title;
    private EditText txt_note;
    private Button btnSave;
    private Button btnDelete;
    private Note note;
    private MyDAO dao;
    private Note chosenNote;
    private Intent shareIntent;
    private String shareBody;
    private String shareTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        txt_title = findViewById(R.id.editText_title);
        txt_note = findViewById(R.id.editText_note);
        btnSave = findViewById(R.id.button_save);
        btnDelete = findViewById(R.id.button_delete);
        dao = MyAppDatabase.getInstance(this).myDAO();


        final Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        // get the current note
        final Intent intent = getIntent();
        if(intent.getExtras()!=null) {
            String chosenNoteId = intent.getStringExtra("CHOSEN_NOTE_ID");
            int id = Integer.parseInt(chosenNoteId);
            chosenNote = dao.getNodeById(id);
            txt_title.setText(chosenNote.getNoteTitle());
            txt_note.setText(chosenNote.getNoteText());

            // get text and title of chosen Note to share
            shareTitle = chosenNote.getNoteTitle();
            shareBody = chosenNote.getNoteText();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_share:
                onShare();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onShare(){
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/pain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void onDelete() {
        if(chosenNote!=null) {
            dao.delete(chosenNote);
        }
        finish();
    }

    public String getTime()
    {
        // TO-DO: add day month year.
        // if the note creates today, don't show the day in recycler view.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = dateFormat.format(calendar.getTime());
        return time;
    }
    public void onSave()
    {
        String Title = txt_title.getText().toString();
        String Note = txt_note.getText().toString();
        String Date = getTime();

        if(!Note.isEmpty() || !Title.isEmpty()) {
            if (chosenNote != null) {
                chosenNote.setNoteText(Note);
                chosenNote.setNoteTitle(Title);
                chosenNote.setNoteDate(Date);
                dao.Update(chosenNote);
            }
            else {
                note = new Note(Title, Note, Date);
                dao.addNote(note);
            }
            finish();
        }
        else
            Toast.makeText(getApplicationContext(),"Please fill in the blanks", Toast.LENGTH_SHORT).show();
    }
}
