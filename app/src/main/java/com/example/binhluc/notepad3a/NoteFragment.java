package com.example.binhluc.notepad3a;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class NoteFragment extends Fragment implements onItemClickListener {
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerViewNote;
    private ArrayList<Note> notes;
    private MyDAO noteDAO;
    public View rootView;
    private EditText searchText;

    public NoteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_note, container, false);
        create();
        LoadAllNotes();
        setHasOptionsMenu(true);
        return rootView;

    }
    private void create()
    {
        // 1. get a reference to recyclerView
        recyclerViewNote = (RecyclerView) rootView.findViewById(R.id.notes_list);
        searchText = (EditText) getActivity().findViewById(R.id.searchText);
        searchText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            public void afterTextChanged(Editable s) {
                String getSearchText = searchText.getText().toString();
                if(!getSearchText.isEmpty()) {
                    LoadSearchNotes(getSearchText);
                }
                else
                {
                    LoadAllNotes();
                }
            }

        });
        // 2. set layoutManger
        recyclerViewNote.setLayoutManager(new LinearLayoutManager(getActivity()));
        noteDAO = MyAppDatabase.getInstance(getActivity()).myDAO();
    }
    private void load() {

        // 3. create an adapter
        recyclerAdapter = new RecyclerAdapter(getActivity(), notes);
        // 4. set adapter
        recyclerViewNote.setHasFixedSize(true);
        recyclerViewNote.setAdapter(recyclerAdapter);
        recyclerAdapter.setListener(this);
        // 5. set item animator to DefaultAnimator
        recyclerViewNote.setItemAnimator(new DefaultItemAnimator());
    }
    @Override
    public void onResume() {
        super.onResume();
        LoadAllNotes();
    }
    public void LoadAllNotes()
    {
        this.notes = new ArrayList<>();
        List<Note> list = noteDAO.getAllNote();
        this.notes.addAll(list);
        load();
    }
    public void LoadSearchNotes(String searchText1)
    {
        searchText1="%"+searchText1+"%";
        this.notes = new ArrayList<>();
        List<Note> list = noteDAO.getNotesListSearch(searchText1);
        this.notes.addAll(list);
        load();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(getActivity(), NoteEdit.class));
                return true;
        }
        return false;
    }

    @Override
    public void onNoteClick(int noteid) {
        String id = Integer.toString(noteid);
        Intent intent = new Intent(getActivity(), NoteEdit.class);
        intent.putExtra("CHOSEN_NOTE_ID", id);
        startActivity(intent);
    }



}
