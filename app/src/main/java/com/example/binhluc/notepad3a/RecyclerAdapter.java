package com.example.binhluc.notepad3a;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Note> notes;
    private Context context;

    //Init callback method
    private onItemClickListener listener;
    public void setListener(onItemClickListener listener){ this.listener = listener;}

    RecyclerAdapter(Context context, ArrayList<Note> notes)
    {
        this.notes = notes;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.note_main, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, final int position) {
        holder.noteTitle.setText(notes.get(position).getNoteTitle());
        holder.noteText.setText(notes.get(position).getNoteText());
        holder.noteDate.setText(notes.get(position).getNoteDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNoteClick(notes.get(position).getId());
            }
        });
        }
    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteText;
        TextView noteDate;
        TextView noteTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteText = itemView.findViewById(R.id.note_text);
            noteDate = itemView.findViewById(R.id.note_date);
            noteTitle = itemView.findViewById(R.id.note_title);
        }
    }
}