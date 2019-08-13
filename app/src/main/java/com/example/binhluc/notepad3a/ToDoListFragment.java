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
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ToDoListFragment extends Fragment implements onToDoTaskClickListener{
    private ToDoTaskAdapter toDoTaskAdapter;
    private RecyclerView recyclerViewTask;
    private ArrayList<ToDoTask> toDoTasks;
    private ToDoTaskDAO toDoTaskDAO;
    private ToDoTask task;
    private View rootView;
    private EditText searchText;

    public ToDoListFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        create();
        LoadAllNotes();
        setHasOptionsMenu(true);
        return rootView;
    }
    private void create() {
        // 1. get a reference to recyclerView
        recyclerViewTask = (RecyclerView) rootView.findViewById(R.id.todo_list);
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
        recyclerViewTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        toDoTaskDAO = ToDoTaskDatabase.getInstance(getActivity()).toDoTaskDAO();
    }
    private void load() {

        // 3. create an adapter
        toDoTaskAdapter = new ToDoTaskAdapter(getActivity(), toDoTasks);
        // 4. set adapter
        recyclerViewTask.setHasFixedSize(true);
        recyclerViewTask.setAdapter(toDoTaskAdapter);
        toDoTaskAdapter.setListener(this);
        // 5. set item animator to DefaultAnimator
        recyclerViewTask.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(getActivity(), ToDoTaskEdit.class));
                return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadAllNotes();
    }
    public void LoadAllNotes()
    {
        this.toDoTasks = new ArrayList<>();
        List<ToDoTask> list = toDoTaskDAO.getallTask();
        this.toDoTasks.addAll(list);
        load();
    }
    public void LoadSearchNotes(String searchText1)
    {
        searchText1="%"+searchText1+"%";
        this.toDoTasks = new ArrayList<>();
        List<ToDoTask> list = toDoTaskDAO.getToDoListSearch(searchText1);
        this.toDoTasks.addAll(list);
        load();
    }
    @Override
    public void onToDoTaskClick(int todotaskid) {
        String id = Integer.toString(todotaskid);
        Intent intent = new Intent(getActivity(), ToDoTaskEdit.class);
        intent.putExtra("CHOSEN_NOTE_ID", id);
        startActivity(intent);
    }

    @Override
    public void onToDoTaskClickDeleteTask(int todotaskid) {
        task = toDoTaskDAO.getTaskById(todotaskid);
        toDoTaskDAO.Delete(task);
        LoadAllNotes();
    }

    @Override
    public void onToDoTaskChecked(boolean isChecked, int todotaskid) {
        task = toDoTaskDAO.getTaskById(todotaskid);
        task.setCheck(isChecked);
        toDoTaskDAO.Update(task);
        LoadAllNotes();
    }
}
