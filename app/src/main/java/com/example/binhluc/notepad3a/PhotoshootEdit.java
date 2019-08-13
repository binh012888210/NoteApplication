package com.example.binhluc.notepad3a;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PhotoshootEdit extends AppCompatActivity {
    private EditText txt_title;
    private EditText txt_note;
    private ImageView imageView;
    private Button btnSave;
    private Button btnDelete;
    private PhotoShoot note;
    private PhotoShootDAO dao;
    private PhotoShoot chosenNote;
    private Bitmap bmp;
    private Bitmap bitmap;
    private byte[] imageBytePrimary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoshoot_edit);
        txt_title = findViewById(R.id.editText_title);
        txt_note = findViewById(R.id.editText_note);
        imageView = findViewById(R.id.image_photoshoot);
        btnSave = findViewById(R.id.button_save);
        btnDelete = findViewById(R.id.button_delete);
        dao = PhotoShootDB.getInstance(this).photoShootDAO();

        // get the current note
        final Intent intent = getIntent();
        Intent intent_gallery = getIntent();
        byte[] imageByte = intent_gallery.getByteArrayExtra("IMAGE_BYTE_GALLERY");
        if(intent.getStringExtra("CHOSEN_NOTE_ID")!=null) {
            String chosenNoteId = intent.getStringExtra("CHOSEN_NOTE_ID");
            int id = Integer.parseInt(chosenNoteId);
            chosenNote = dao.getNodeById(id);
            txt_title.setText(chosenNote.getNoteTitle());
            txt_note.setText(chosenNote.getNoteText());

            Bitmap bitmap = BitmapFactory.decodeByteArray(chosenNote.getNoteImage(), 0 , chosenNote.getNoteImage().length);
            imageView.setImageBitmap(bitmap);
        }
        else if (imageByte!= null){
            bitmap = BitmapFactory.decodeByteArray(imageByte, 0 , imageByte.length);
            imageView.setImageBitmap(bitmap);
            imageBytePrimary = BitmapToByte(bitmap);
        }
        else {
            //get image
            Bundle extras = getIntent().getExtras();
            bmp = (Bitmap) extras.getParcelable("IMAGE_BITMAP");
            imageView.setImageBitmap(bmp);
            imageBytePrimary = BitmapToByte(bmp);
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
    public String getTime()
    {
        // TO-DO: add day month year.
        // if the note creates today, don't show the day in recycler view.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String time = dateFormat.format(calendar.getTime());
        return time;
    }
    private void onSave() {
        String Title = txt_title.getText().toString();
        String Note = txt_note.getText().toString();
        String Date = getTime();
        if(!Note.isEmpty() || !Title.isEmpty()) {
            if (chosenNote != null) {
                chosenNote.setNoteText(Note);
                chosenNote.setNoteTitle(Title);
                chosenNote.setNoteDate(Date);
                dao.Update(chosenNote);
                Toast.makeText(getApplicationContext(), "Successfully updated", Toast.LENGTH_SHORT).show();
            }
            else {
                note = new PhotoShoot(Title, Note, Date,imageBytePrimary);
                dao.addNote(note);
                Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
            }

            finish();
        }
        else
            Toast.makeText(getApplicationContext(),"Please fill in the blanks", Toast.LENGTH_SHORT).show();
    }
    private byte[] BitmapToByte(Bitmap image)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public void onDelete()
    {
        if (chosenNote != null) {
            dao.delete(chosenNote);
        }
        finish();
    }
}
